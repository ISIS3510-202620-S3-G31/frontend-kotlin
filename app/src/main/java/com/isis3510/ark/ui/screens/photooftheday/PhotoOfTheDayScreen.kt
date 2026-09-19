package com.isis3510.ark.ui.screens.photooftheday


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isis3510.ark.ui.navigation.ArkDestination
import com.isis3510.ark.ui.theme.ArkBackground
import com.isis3510.ark.ui.theme.ArkOnDarkMuted
import com.isis3510.ark.ui.theme.ArkPrimary
import com.isis3510.ark.ui.theme.ArkSuccess
import com.isis3510.ark.ui.theme.ArkText
import com.isis3510.ark.ui.theme.ArkTextMuted
import com.isis3510.ark.ui.theme.ArkTheme

private enum class DayState(val description: String) {
    DONE("photo taken"),
    TODAY("today"),
    FUTURE("not yet"),
}

private data class Day(val label: String, val name: String, val state: DayState)

// datos fijos como en la imagen: L-J hechos, viernes es hoy
private const val TODAY_LONG = "Friday, September 18"
private const val TODAY_SHORT = "Sep 18"

private val week = listOf(
    Day("M", "Monday", DayState.DONE),
    Day("T", "Tuesday", DayState.DONE),
    Day("W", "Wednesday", DayState.DONE),
    Day("T", "Thursday", DayState.DONE),
    Day("F", "Friday", DayState.TODAY),
    Day("S", "Saturday", DayState.FUTURE),
    Day("S", "Sunday", DayState.FUTURE),
)

@Composable
fun PhotoOfTheDayScreen(onBack: () -> Unit) {
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
            WeekStrip()
            Viewfinder(
                onFlash = {},
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            )
        }

        CaptureControls(
            onGallery = {},
            onShutter = {},
            onFlipCamera = {},
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 8.dp, bottom = 24.dp),
        )
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
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ArkDestination.PhotoOfTheDay.title,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(text = TODAY_LONG, style = MaterialTheme.typography.bodyMedium, color = ArkTextMuted)
        }
    }
}

// boton redondo de 48dp (minimo tactil)
@Composable
private fun CircleButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(containerColor)
            .clickable(role = Role.Button, onClickLabel = contentDescription, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(imageVector = icon, contentDescription = contentDescription, modifier = Modifier.size(22.dp))
    }
}

@Composable
private fun WeekStrip(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 8.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        week.forEach { day -> DayDot(day) }
    }
}

@Composable
private fun DayDot(day: Day) {
    Column(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "${day.name}, ${day.state.description}" },
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (day.state) {
            DayState.DONE -> Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(ArkSuccess, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(imageVector = CheckIcon, contentDescription = null, modifier = Modifier.size(16.dp))
            }

            DayState.TODAY -> Box(
                modifier = Modifier
                    .size(36.dp)
                    .border(2.dp, ArkPrimary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(ArkPrimary, CircleShape),
                )
            }

            DayState.FUTURE -> Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(ArkText.copy(alpha = 0.1f), CircleShape),
            )
        }
        Text(
            text = day.label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (day.state == DayState.TODAY) ArkText else ArkTextMuted,
        )
    }
}

@Composable
private fun Viewfinder(onFlash: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(456.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.inverseSurface),
    ) {
        // grid de la regla de tercios
        Canvas(modifier = Modifier.fillMaxSize()) {
            val lineColor = ArkBackground.copy(alpha = 0.15f)
            val stroke = 1.dp.toPx()
            val w = size.width
            val h = size.height
            drawLine(lineColor, Offset(w / 3f, 0f), Offset(w / 3f, h), strokeWidth = stroke)
            drawLine(lineColor, Offset(w * 2 / 3f, 0f), Offset(w * 2 / 3f, h), strokeWidth = stroke)
            drawLine(lineColor, Offset(0f, h / 3f), Offset(w, h / 3f), strokeWidth = stroke)
            drawLine(lineColor, Offset(0f, h * 2 / 3f), Offset(w, h * 2 / 3f), strokeWidth = stroke)
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(ArkBackground.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(imageVector = CameraIcon, contentDescription = null, modifier = Modifier.size(34.dp))
            }
            Text(
                text = "Frame your moment today",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 260.dp),
            )
            Text(
                text = "One photo a day, one new memory",
                style = MaterialTheme.typography.bodyMedium,
                color = ArkOnDarkMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 260.dp),
            )
        }

        // overlay superior: fecha + flash
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = TODAY_SHORT,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
            )
            CircleButton(
                icon = FlashIcon,
                contentDescription = "Flash",
                onClick = onFlash,
                containerColor = ArkBackground.copy(alpha = 0.16f),
            )
        }
    }
}

@Composable
private fun CaptureControls(
    onGallery: () -> Unit,
    onShutter: () -> Unit,
    onFlipCamera: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleButton(icon = GalleryIcon, contentDescription = "Open the gallery", onClick = onGallery)
        ShutterButton(onClick = onShutter)
        CircleButton(icon = FlipCameraIcon, contentDescription = "Flip the camera", onClick = onFlipCamera)
    }
}

@Composable
private fun ShutterButton(onClick: () -> Unit) {
    // se encoge un poquito mientras esta presionado
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier = Modifier
            .size(84.dp)
            .scale(if (pressed) 0.94f else 1f)
            .clip(CircleShape)
            .border(3.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                role = Role.Button,
                onClickLabel = "Take the photo",
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
        )
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun PhotoOfTheDayPreview() {
    ArkTheme {
        PhotoOfTheDayScreen(onBack = {})
    }
}

// iconos sacados del figma

private fun strokeIcon(name: String, size: Float, pathData: String, strokeWidth: Float): ImageVector =
    ImageVector.Builder(name = name, defaultWidth = size.dp, defaultHeight = size.dp, viewportWidth = size, viewportHeight = size)
        .addPath(
            pathData = addPathNodes(pathData),
            stroke = SolidColor(ArkText),
            strokeLineWidth = strokeWidth,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        )
        .build()

private val ChevronLeftIcon: ImageVector by lazy {
    strokeIcon("ChevronLeft", 22f, "M13.75 4.58333L7.33333 11L13.75 17.4167", 2.93333f)
}

private val CheckIcon: ImageVector by lazy {
    strokeIcon("Check", 16f, "M3.33333 8.33333L6.66667 11.6667L12.6667 5", 2.26667f)
}

private val FlipCameraIcon: ImageVector by lazy {
    strokeIcon(
        "FlipCamera",
        22f,
        "M3.66667 11C3.66667 6.96667 6.96667 3.66667 11 3.66667C13.2 3.66667 15.125 4.58333 16.5 6.14167L18.3333 8.06667M14.1167 8.06667H18.3333V3.85M18.3333 11C18.3333 15.0333 15.0333 18.3333 11 18.3333C8.8 18.3333 6.875 17.4167 5.5 15.8583L3.66667 13.9333M7.88333 13.9333H3.66667V18.15",
        2.38333f,
    )
}

private val GalleryIcon: ImageVector by lazy {
    ImageVector.Builder(name = "Gallery", defaultWidth = 22.dp, defaultHeight = 22.dp, viewportWidth = 22f, viewportHeight = 22f)
        .addPath(
            pathData = addPathNodes(
                "M5.5 2.75H16.5C18.0583 2.75 19.25 3.94167 19.25 5.5V16.5C19.25 18.0583 18.0583 19.25 16.5 19.25H5.5C3.94167 19.25 2.75 18.0583 2.75 16.5V5.5C2.75 3.94167 3.94167 2.75 5.5 2.75ZM6.14167 7.79167C6.14167 8.70833 6.875 9.44167 7.79167 9.44167C8.70833 9.44167 9.44167 8.70833 9.44167 7.79167C9.44167 6.875 8.70833 6.14167 7.79167 6.14167C6.875 6.14167 6.14167 6.875 6.14167 7.79167ZM5.04167 16.9583L8.70833 12.1917L11.4583 15.0333L13.75 12.65L16.9583 16.9583H5.04167Z",
            ),
            fill = SolidColor(ArkText),
            pathFillType = PathFillType.EvenOdd,
        )
        .build()
}

private val FlashIcon: ImageVector by lazy {
    ImageVector.Builder(name = "Flash", defaultWidth = 22.dp, defaultHeight = 22.dp, viewportWidth = 22f, viewportHeight = 22f)
        .addPath(
            pathData = addPathNodes("M12.375 2.29167L4.58333 12.375H10.0833L9.16667 19.7083L17.4167 9.16667H11.9167L12.375 2.29167Z"),
            fill = SolidColor(ArkBackground),
            stroke = SolidColor(ArkBackground),
            strokeLineWidth = 1.46667f,
            strokeLineJoin = StrokeJoin.Round,
        )
        .build()
}

private val CameraIcon: ImageVector by lazy {
    ImageVector.Builder(name = "Camera", defaultWidth = 34.dp, defaultHeight = 34.dp, viewportWidth = 34f, viewportHeight = 34f)
        .addPath(
            pathData = addPathNodes(
                "M12 10L13.4 6.9C13.8 6 14.5 5.5 15.5 5.5H18.5C19.5 5.5 20.2 6 20.6 6.9L22 10H26C28.8 10 31 12.2 31 15V24C31 26.8 28.8 29 26 29H8C5.2 29 3 26.8 3 24V15C3 12.2 5.2 10 8 10H12ZM11 20C11 23.3 13.7 26 17 26C20.3 26 23 23.3 23 20C23 16.7 20.3 14 17 14C13.7 14 11 16.7 11 20ZM14.2 20C14.2 21.5 15.5 22.8 17 22.8C18.5 22.8 19.8 21.5 19.8 20C19.8 18.5 18.5 17.2 17 17.2C15.5 17.2 14.2 18.5 14.2 20Z",
            ),
            fill = SolidColor(ArkBackground),
            pathFillType = PathFillType.EvenOdd,
        )
        .build()
}