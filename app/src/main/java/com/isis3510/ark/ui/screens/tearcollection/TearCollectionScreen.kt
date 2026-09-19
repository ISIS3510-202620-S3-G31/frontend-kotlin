package com.isis3510.ark.ui.screens.tearcollection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isis3510.ark.ui.theme.*

private val ArkCardSurface = Color(0xFFFDF3E0)

@Composable
fun TearCollectionScreen(
    onBack: () -> Unit,
    viewModel: TearCollectionViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state.currentStep) {
        TearViewStep.SANCTUARY_LIST -> {
            TearSanctuaryContent(
                tears = state.tears,
                onSelectTear = { viewModel.selectTear(it) },
                onOpenNewTear = { viewModel.openLogNewTear() },
                onBack = onBack
            )
        }
        TearViewStep.TEAR_DETAIL -> {
            state.selectedTear?.let { tear ->
                TearDetailContent(
                    tear = tear,
                    isSaved = state.isSavedToInsights,
                    onGoToTool = onBack,
                    onSave = { viewModel.saveToInsights() },
                    onBack = { viewModel.backToSanctuary() }
                )
            }
        }
        TearViewStep.LOG_NEW_TEAR -> {
            LogNewTearContent(
                onSaveTear = { reason, relief ->
                    viewModel.addTear(reason, relief)
                },
                onBack = { viewModel.backToSanctuary() }
            )
        }
    }
}

@Composable
private fun TearSanctuaryContent(
    tears: List<TearItem>,
    onSelectTear: (TearItem) -> Unit,
    onOpenNewTear: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArkBackground)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .shadow(3.dp, CircleShape)
                    .background(ArkCardSurface, CircleShape)
            ) {
                Text(text = "‹", fontSize = 26.sp, color = ArkText, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Tear Collection",
                    fontFamily = Sorean,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkText
                )
                Text(
                    text = "Your emotional sanctuary",
                    fontFamily = Figtree,
                    fontSize = 13.sp,
                    color = ArkTextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Central Glass Jar Illustration with Floating Tears and Mascot
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            // Jar Container
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(170.dp)
                    .shadow(4.dp, RoundedCornerShape(28.dp))
                    .background(ArkCardSurface.copy(alpha = 0.85f), RoundedCornerShape(28.dp))
                    .border(2.dp, ArkSecondary.copy(alpha = 0.6f), RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Lid
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                        .width(70.dp)
                        .height(12.dp)
                        .background(ArkSurfaceDim, RoundedCornerShape(6.dp))
                )

                // Floating colorful tears inside the jar
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(text = "💧", fontSize = 18.sp)
                        Text(text = "🔸", fontSize = 14.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        Text(text = "💧", fontSize = 24.sp)
                        Text(text = "💧", fontSize = 16.sp)
                        Text(text = "🌸", fontSize = 14.sp)
                    }
                }
            }

            // Peaceful Bloom mascot beside jar
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 12.dp, bottom = 12.dp)
                    .size(64.dp)
                    .shadow(3.dp, CircleShape)
                    .background(ArkCardSurface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🌱", fontSize = 14.sp)
                    Text(text = "😌", fontSize = 24.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Counter pill
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "💧", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${tears.size} tears preserved",
                fontFamily = Figtree,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of preserved tears
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tears) { tear ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectTear(tear) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = ArkCardSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Drop icon
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(tear.colorTone.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "💧", fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tear.reason,
                                fontFamily = Figtree,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ArkText
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${tear.dateFormatted}, ${tear.timeFormatted}",
                                fontFamily = Figtree,
                                fontSize = 11.sp,
                                color = ArkTextMuted
                            )
                        }

                        Text(
                            text = "›",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = ArkTextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CTA Button
        Button(
            onClick = onOpenNewTear,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.5.dp, ArkText, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ArkPrimary,
                contentColor = ArkText
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Preserve a New Tear",
                fontFamily = Figtree,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TearDetailContent(
    tear: TearItem,
    isSaved: Boolean,
    onGoToTool: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArkBackground)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .shadow(3.dp, CircleShape)
                    .background(ArkCardSurface, CircleShape)
            ) {
                Text(text = "‹", fontSize = 26.sp, color = ArkText, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = "Tear Detail",
                fontFamily = Sorean,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Large Glowing Teardrop Visual
        Box(
            modifier = Modifier
                .size(110.dp)
                .shadow(12.dp, CircleShape, spotColor = ArkPrimary)
                .background(
                    Brush.radialGradient(
                        colors = listOf(ArkSecondary.copy(alpha = 0.4f), Color.Transparent)
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .shadow(6.dp, RoundedCornerShape(topStart = 0.dp, topEnd = 38.dp, bottomStart = 38.dp, bottomEnd = 38.dp))
                    .background(
                        Brush.linearGradient(listOf(ArkSecondary, ArkPrimary)),
                        RoundedCornerShape(topStart = 0.dp, topEnd = 38.dp, bottomStart = 38.dp, bottomEnd = 38.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${tear.dateFormatted}, 2026 — ${tear.timeFormatted}",
            fontFamily = Figtree,
            fontSize = 12.sp,
            color = ArkTextMuted
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Main Detail Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ArkCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Section 1: Reason
                Text(
                    text = "REASON HONORED",
                    fontFamily = Figtree,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkSecondary,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tear.reason,
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArkText
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = ArkSurfaceDim)
                Spacer(modifier = Modifier.height(14.dp))

                // Section 2: Root Emotion
                Text(
                    text = "ROOT EMOTION IDENTIFIED",
                    fontFamily = Figtree,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkSecondary,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tear.rootEmotion,
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArkText
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = ArkSurfaceDim)
                Spacer(modifier = Modifier.height(14.dp))

                // Section 3: Relief Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RELIEF LEVEL",
                        fontFamily = Figtree,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ArkSecondary,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Lv. ${tear.reliefLevel}/10",
                        fontFamily = Figtree,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ArkPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { tear.reliefLevel / 10f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = ArkPrimary,
                    trackColor = ArkSurfaceDim
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = ArkSurfaceDim)
                Spacer(modifier = Modifier.height(14.dp))

                // Section 4: Reflection
                Row(verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "COMPASSION REFLECTION",
                            fontFamily = Figtree,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ArkSecondary,
                            letterSpacing = 1.2.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tear.reflection,
                            fontFamily = Figtree,
                            fontSize = 13.sp,
                            color = ArkText,
                            lineHeight = 19.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Mini Bloom with heart
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(ArkBackground, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🌱", fontSize = 10.sp)
                            Text(text = "❤️", fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Primary Action
        Button(
            onClick = onGoToTool,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.5.dp, ArkText, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ArkPrimary,
                contentColor = ArkText
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Go to Recommended Tool",
                fontFamily = Figtree,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Secondary Text Button
        TextButton(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isSaved) "✓ Saved to Insights!" else "Save to My Insights & Finish",
                fontFamily = Figtree,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ArkSecondary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LogNewTearContent(
    onSaveTear: (String, Int) -> Unit,
    onBack: () -> Unit
) {
    var selectedReason by remember { mutableStateOf("Overwhelm & deadline stress") }
    var reliefLevel by remember { mutableFloatStateOf(8f) }

    val reasons = listOf(
        "Overwhelm & deadline stress",
        "Missing someone deeply",
        "Relief after a hard conversation",
        "Exhaustion and physical burnout",
        "Tears of gratitude and release"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArkBackground)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .shadow(3.dp, CircleShape)
                    .background(ArkCardSurface, CircleShape)
            ) {
                Text(text = "‹", fontSize = 26.sp, color = ArkText, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "Preserve a Tear",
                fontFamily = Sorean,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ArkCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "What brought the tears?",
                    fontFamily = Figtree,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkText
                )

                Spacer(modifier = Modifier.height(14.dp))

                reasons.forEach { reason ->
                    val isSelected = selectedReason == reason
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(
                                color = if (isSelected) ArkSecondary.copy(alpha = 0.12f) else ArkBackground.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .border(
                                width = if (isSelected) 1.5.dp else 0.dp,
                                color = if (isSelected) ArkSecondary else Color.Transparent,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { selectedReason = reason }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isSelected) "💧" else "○",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = reason,
                            fontFamily = Figtree,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = ArkText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Relief level after crying: ${reliefLevel.toInt()}/10",
                    fontFamily = Figtree,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkText
                )
                Slider(
                    value = reliefLevel,
                    onValueChange = { reliefLevel = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = ArkPrimary,
                        activeTrackColor = ArkPrimary,
                        inactiveTrackColor = ArkSurfaceDim
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { onSaveTear(selectedReason, reliefLevel.toInt()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.5.dp, ArkText, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ArkPrimary,
                contentColor = ArkText
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Place in Sanctuary",
                fontFamily = Figtree,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
