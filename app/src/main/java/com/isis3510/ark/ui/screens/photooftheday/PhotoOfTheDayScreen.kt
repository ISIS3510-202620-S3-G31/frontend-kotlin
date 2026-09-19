package com.isis3510.ark.ui.screens.screamtank

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isis3510.ark.ui.navigation.ArkDestination
import com.isis3510.ark.ui.theme.ArkAccent
import com.isis3510.ark.ui.theme.ArkBackground
import com.isis3510.ark.ui.theme.ArkPrimary
import com.isis3510.ark.ui.theme.ArkSecondary
import com.isis3510.ark.ui.theme.ArkText
import com.isis3510.ark.ui.theme.ArkTextMuted
import com.isis3510.ark.ui.theme.ArkTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

private const val MIN_DB = 40.0
private const val MAX_DB = 100.0

private fun levelFromDb(db: Double): Float =
    (((db - MIN_DB) / (MAX_DB - MIN_DB)).coerceIn(0.0, 1.0)).toFloat()

private fun zoneLabel(db: Double): String = when {
    db < 60 -> "Soft"
    db < 80 -> "Medium"
    else -> "Strong"
}

@Composable
fun ScreamTankScreen(onBack: () -> Unit) {
    var listening by remember { mutableStateOf(false) }
    var db by remember { mutableFloatStateOf(MIN_DB.toFloat()) }
    var tankLevel by remember { mutableFloatStateOf(0.7f) } // arranca en 70% como en la imagen

    // por ahora el grito es simulado: mientras escucha, los dB suben y bajan al azar
    // y el tanque se va llenando
    LaunchedEffect(listening) {
        if (listening) {
            while (true) {
                val target = Random.nextDouble(55.0, 100.0)
                db = target.toFloat()
                if (target > 60.0) {
                    tankLevel = (tankLevel + levelFromDb(target) * 0.01f).coerceIn(0f, 1f)
                }
                delay(120)
            }
        } else {
            db = MIN_DB.toFloat()
        }
    }

    ScreamTankContent(
        listening = listening,
        db = db,
        tankLevel = tankLevel,
        onBack = onBack,
        onReset = { tankLevel = 0f },
        onToggleMic = { listening = !listening },
        onFinish = { listening = false },
    )
}

@Composable
private fun ScreamTankContent(
    listening: Boolean,
    db: Float,
    tankLevel: Float,
    onBack: () -> Unit,
    onReset: () -> Unit,
    onToggleMic: () -> Unit,
    onFinish: () -> Unit,
) {
    val micLevel = levelFromDb(db.toDouble())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding(),
    ) {
        Header(onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Stage(
                listening = listening,
                tankLevel = tankLevel,
                waveLevel = micLevel,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            )
            IntensityMeter(db = db)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 8.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircleButton(icon = RestartIcon, contentDescription = "Reset the tank", onClick = onReset)
            MicButton(listening = listening, level = micLevel, onClick = onToggleMic)
            CircleButton(icon = CheckIcon, contentDescription = "Finish the session", onClick = onFinish)
        }
    }
}

@Composable
private fun Header(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleButton(icon = ChevronLeftIcon, contentDescription = "Back to the toolbox", onClick = onBack)
        Text(
            text = ArkDestination.ScreamTank.title,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
    }
}

// boton redondo de 48dp (minimo tactil)
@Composable
private fun CircleButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(role = Role.Button, onClickLabel = contentDescription, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(imageVector = icon, contentDescription = contentDescription, modifier = Modifier.size(22.dp))
    }
}

@Composable
private fun Stage(listening: Boolean, tankLevel: Float, waveLevel: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(480.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.inverseSurface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TankIllustration(tankLevel = tankLevel, waveLevel = waveLevel)
            Text(
                text = "Let it all out!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                textAlign = TextAlign.Center,
            )
        }
        ListeningPill(
            listening = listening,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
        )
    }
}

@Composable
private fun ListeningPill(listening: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, CircleShape)
            .padding(start = 12.dp, top = 8.dp, end = 14.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(if (listening) ArkAccent else ArkText.copy(alpha = 0.3f), CircleShape),
        )
        Text(
            text = if (listening) "Listening" else "Paused",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun TankIllustration(tankLevel: Float, waveLevel: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(width = 294.dp, height = 250.dp)) {
        VoiceWaves(level = waveLevel)
        Box(
            modifier = Modifier
                .offset(x = 90.dp, y = 2.dp)
                .size(width = 115.dp, height = 246.dp),
        ) {
            Image(imageVector = TankBody, contentDescription = null, modifier = Modifier.fillMaxSize())
            TankGauge(level = tankLevel, modifier = Modifier.offset(x = 50.dp, y = 61.5.dp))
        }
        Text(
            text = "${(tankLevel * 100).roundToInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier.offset(x = 228.dp),
        )
    }
}

@Composable
private fun VoiceWaves(level: Float, modifier: Modifier = Modifier) {
    val animatedLevel = remember { Animatable(0f) }
    LaunchedEffect(level) {
        animatedLevel.animateTo(level, animationSpec = tween(150))
    }
    Image(
        imageVector = VoiceWavesArt,
        contentDescription = null,
        modifier = modifier
            .size(width = 80.dp, height = 250.dp)
            .graphicsLayer {
                val bulge = 1f + animatedLevel.value * 0.15f
                scaleX = bulge
                scaleY = bulge
                transformOrigin = TransformOrigin(0.3f, 0.5f)
            },
    )
}

// en el figma el medidor tiene 10 niveles pero la ventanita solo muestra los 5 de arriba,
// por eso con 70% se ven 2 prendidos
private const val GAUGE_LEVELS = 10
private const val GAUGE_VISIBLE = 5

@Composable
private fun TankGauge(level: Float, modifier: Modifier = Modifier) {
    val litLevels = (level.coerceIn(0f, 1f) * GAUGE_LEVELS).roundToInt()
    Box(
        modifier = modifier
            .size(width = 15.dp, height = 115.dp)
            .background(ArkText, CircleShape),
    ) {
        Column(
            modifier = Modifier.padding(start = 2.dp, top = 5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            for (i in 0 until GAUGE_VISIBLE) {
                val segmentLevel = GAUGE_LEVELS - i
                val isLit = segmentLevel <= litLevels
                Box(
                    modifier = Modifier
                        .size(width = 12.dp, height = 18.dp)
                        .background(
                            color = if (isLit) ArkAccent else ArkBackground.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp),
                        ),
                )
            }
        }
    }
}

// 12 segmentos + valor en dB + zona
@Composable
private fun IntensityMeter(db: Float, modifier: Modifier = Modifier) {
    val lit = (levelFromDb(db.toDouble()) * 12).roundToInt()

    fun zoneColor(i: Int): Color = when {
        i < 4 -> ArkSecondary
        i < 8 -> ArkPrimary
        else -> ArkAccent
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 4.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Intensity", style = MaterialTheme.typography.bodyMedium, color = ArkTextMuted)
            Text("${db.roundToInt()} dB · ${zoneLabel(db.toDouble())}", style = MaterialTheme.typography.titleMedium)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in 0 until 12) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp)
                        .background(if (i < lit) zoneColor(i) else ArkText.copy(alpha = 0.1f), RoundedCornerShape(6.dp)),
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Soft", style = MaterialTheme.typography.bodyMedium, color = ArkTextMuted)
            Text("Medium", style = MaterialTheme.typography.bodyMedium, color = ArkTextMuted)
            Text("Strong", style = MaterialTheme.typography.bodyMedium, color = ArkTextMuted)
        }
    }
}

@Composable
private fun MicButton(listening: Boolean, level: Float, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val animatedLevel = remember { Animatable(0f) }
    LaunchedEffect(level, listening) {
        animatedLevel.animateTo(if (listening) level else 0f, animationSpec = tween(150))
    }
    val description = if (listening) "Pause listening" else "Start listening"
    Box(
        modifier = modifier
            .size(116.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false),
                role = Role.Button,
                onClickLabel = description,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (listening) {
            Box(
                modifier = Modifier
                    .size(116.dp)
                    .scale(1f + 0.15f * animatedLevel.value)
                    .background(ArkPrimary.copy(alpha = 0.15f), CircleShape),
            )
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(1f + 0.08f * animatedLevel.value)
                    .background(ArkPrimary.copy(alpha = 0.30f), CircleShape),
            )
        }
        Box(
            modifier = Modifier
                .size(76.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Image(imageVector = MicIcon, contentDescription = description, modifier = Modifier.size(34.dp))
        }
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ScreamTankPreview() {
    ArkTheme {
        ScreamTankContent(
            listening = true,
            db = 84f,
            tankLevel = 0.7f,
            onBack = {},
            onReset = {},
            onToggleMic = {},
            onFinish = {},
        )
    }
}

// vectores sacados del figma

private val TankCapDark = Color(0xFF46423E)
private val TankCapLight = Color(0xFF686664)

private fun strokeIcon(name: String, pathData: String, strokeWidth: Float): ImageVector =
    ImageVector.Builder(name = name, defaultWidth = 22.dp, defaultHeight = 22.dp, viewportWidth = 22f, viewportHeight = 22f)
        .addPath(
            pathData = addPathNodes(pathData),
            stroke = SolidColor(ArkText),
            strokeLineWidth = strokeWidth,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        )
        .build()

private val ChevronLeftIcon: ImageVector by lazy {
    strokeIcon("ChevronLeft", "M13.75 4.58333L7.33333 11L13.75 17.4167", 2.93333f)
}

private val CheckIcon: ImageVector by lazy {
    strokeIcon("Check", "M4.58333 11.4583L9.16667 16.0417L17.4167 6.875", 2.75f)
}

private val RestartIcon: ImageVector by lazy {
    strokeIcon(
        "Restart",
        "M4.125 11C4.125 14.7583 7.24167 17.875 11 17.875C14.7583 17.875 17.875 14.7583 17.875 11C17.875 7.24167 14.7583 4.125 11 4.125C8.8 4.125 6.875 5.13333 5.59167 6.78333M5.04167 2.75V7.15H9.44167",
        2.38333f,
    )
}

// el mic viene dibujado dentro del boton de 116, toca moverlo a su caja de 34
private val MicIcon: ImageVector by lazy {
    ImageVector.Builder(name = "Mic", defaultWidth = 34.dp, defaultHeight = 34.dp, viewportWidth = 34f, viewportHeight = 34f)
        .addGroup(translationX = -41f, translationY = -41f)
        .addPath(
            pathData = addPathNodes(
                "M58 44.5417C54.8833 44.5417 52.3333 47.0917 52.3333 50.2083V57.2917C52.3333 60.4083 54.8833 62.9583 58 62.9583C61.1167 62.9583 63.6667 60.4083 63.6667 57.2917V50.2083C63.6667 47.0917 61.1167 44.5417 58 44.5417Z",
            ),
            fill = SolidColor(ArkText),
        )
        .addPath(
            pathData = addPathNodes(
                "M48.0833 56.5833C48.0833 62.1083 52.475 66.5 58 66.5M58 66.5C63.525 66.5 67.9167 62.1083 67.9167 56.5833M58 66.5V71.4583M53.0417 71.4583H62.9583",
            ),
            stroke = SolidColor(ArkText),
            strokeLineWidth = 3.11667f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        )
        .clearGroup()
        .build()
}

private val VoiceWavesArt: ImageVector by lazy {
    fun ImageVector.Builder.wave(pathData: String, alpha: Float) = addPath(
        pathData = addPathNodes(pathData),
        stroke = SolidColor(ArkBackground),
        strokeAlpha = alpha,
        strokeLineWidth = 3.5f,
        strokeLineCap = StrokeCap.Round,
    )
    ImageVector.Builder(name = "VoiceWaves", defaultWidth = 80.dp, defaultHeight = 250.dp, viewportWidth = 80f, viewportHeight = 250f)
        .wave("M28.4 109.6C32.0128 113.918 33.9924 119.37 33.9924 125C33.9924 130.63 32.0128 136.082 28.4 140.4", 0.3f)
        .wave("M42.2 98C48.5486 105.565 52.0286 115.124 52.0286 125C52.0286 134.876 48.5486 144.435 42.2 152", 0.5f)
        .wave("M56 86.4C65.0844 97.2107 70.0648 110.879 70.0648 125C70.0648 139.121 65.0844 152.789 56 163.6", 0.7f)
        .build()
}

private const val CAP_BEVEL = "M6.9007 0.5L1.4007 6H113.901L105.901 0.5H6.9007Z"
private const val CAP_NECK =
    "M6.4007 36.016C6.24159 35.9175 5.11734 35.0409 1.4007 32H114.401L109.401 36V43L114.401 47H1.4007L6.4007 42.5V36.016Z"
private const val CAP_OUTLINE =
    "M6.4007 36C6.56737 36.1667 5.8007 35.6 1.4007 32H114.401L109.401 36M109.401 36H6.4007V42.5M6.4007 42.5L1.4007 47H114.401L109.401 43M6.4007 42.5C28.5674 42.5 80.2007 42.6 109.401 43M109.401 43V36M1.4007 6L6.9007 0.5H105.901L113.901 6H1.4007Z"

// la tapa de abajo es la misma de arriba girada 180
private val TankBody: ImageVector by lazy {
    fun ImageVector.Builder.block(pathData: String) = addPath(pathData = addPathNodes(pathData), fill = SolidColor(ArkPrimary))
    fun ImageVector.Builder.cap(color: Color) = this
        .addPath(pathData = addPathNodes(CAP_BEVEL), fill = SolidColor(color))
        .addPath(pathData = addPathNodes(CAP_NECK), fill = SolidColor(color))
        .addPath(pathData = addPathNodes(CAP_OUTLINE), stroke = SolidColor(ArkText), strokeLineWidth = 1f)

    ImageVector.Builder(name = "TankBody", defaultWidth = 115.dp, defaultHeight = 246.dp, viewportWidth = 115f, viewportHeight = 246f)
        .block("M1 6.5H114V32.5H1Z")
        .block("M1 47.5H114V198.5H1Z")
        .addGroup(translationX = -0.4f, translationY = 0.5f)
        .cap(TankCapDark)
        .clearGroup()
        .block("M1 213.5H114V239.5H1Z")
        .addGroup(rotate = 180f, pivotX = 57.5f, pivotY = 221.75f)
        .addGroup(translationX = -0.4f, translationY = 198f)
        .cap(TankCapLight)
        .clearGroup()
        .clearGroup()
        .build()
}