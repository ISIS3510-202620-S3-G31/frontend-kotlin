package com.isis3510.ark.ui.screens.emotiondetective

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isis3510.ark.R
import com.isis3510.ark.ui.theme.*

private val ArkCardSurface = Color(0xFFFDF3E0)

@Composable
fun EmotionDetectiveScreen(
    onBack: () -> Unit,
    viewModel: EmotionDetectiveViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state.currentStep) {
        DetectiveStep.INTRO -> {
            DetectiveIntroContent(
                onStart = { viewModel.startInvestigation() },
                onBack = onBack
            )
        }
        DetectiveStep.QUESTIONS -> {
            val questions = state.questions
            val currentIndex = state.currentQuestionIndex
            if (questions.isNotEmpty() && currentIndex < questions.size) {
                val currentQuestion = questions[currentIndex]
                DetectiveQuestionContent(
                    question = currentQuestion,
                    selectedOptionId = state.selectedAnswers[currentQuestion.id],
                    onSelectOption = { optionId ->
                        viewModel.selectOption(currentQuestion.id, optionId)
                    },
                    onContinue = { viewModel.nextQuestion() },
                    onBack = { viewModel.previousQuestion() }
                )
            }
        }
        DetectiveStep.SUMMARY -> {
            state.result?.let { result ->
                DetectiveSummaryContent(
                    result = result,
                    isSaved = state.isSaved,
                    onGoToTool = onBack,
                    onSave = { viewModel.saveToInsights() },
                    onBack = {
                        viewModel.resetInvestigation()
                        onBack()
                    }
                )
            }
        }
    }
}

@Composable
private fun DetectiveIntroContent(
    onStart: () -> Unit,
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

        // Back button
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .shadow(3.dp, CircleShape)
                    .background(ArkCardSurface, CircleShape)
            ) {
                Text(text = "‹", fontSize = 26.sp, color = ArkText, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Screen title
        Text(
            text = "Meet Sprout Detective!",
            fontFamily = Sorean,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = ArkText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Official Mascot Illustration (detective.png)
        Image(
            painter = painterResource(id = R.drawable.detective),
            contentDescription = "Sprout Detective",
            modifier = Modifier
                .size(170.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Welcome Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ArkCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Hi there! I'm Sprout Detective. Let's explore your feelings together and solve the case of what's on your mind. Ready to investigate?",
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    color = ArkText,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Start CTA Button
        Button(
            onClick = onStart,
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
                text = "Let's Investigate!",
                fontFamily = Figtree,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun DetectiveQuestionContent(
    question: DetectiveQuestion,
    selectedOptionId: Int?,
    onSelectOption: (Int) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArkBackground)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Emotional Detective",
                fontFamily = Sorean,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Clue ${question.clueNumber} of ${question.totalClues}",
                fontFamily = Figtree,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = ArkSecondary
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Progress bar
        LinearProgressIndicator(
            progress = { question.clueNumber.toFloat() / question.totalClues },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = ArkSecondary,
            trackColor = ArkSurfaceDim
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ArkCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon Badge
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(ArkSecondary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🔍", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Question Title
                Text(
                    text = question.questionText,
                    fontFamily = Figtree,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkText,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Options list
                question.options.forEach { option ->
                    val isSelected = selectedOptionId == option.id

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .shadow(if (isSelected) 2.dp else 0.dp, RoundedCornerShape(16.dp))
                            .background(
                                color = if (isSelected) ArkSecondary.copy(alpha = 0.12f) else ArkBackground.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) ArkSecondary else Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onSelectOption(option.id) }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Radio circle
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) ArkSecondary else ArkText.copy(alpha = 0.35f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(ArkSecondary, CircleShape)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = option.text,
                            fontFamily = Figtree,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = ArkText
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Continue Button
        Button(
            onClick = onContinue,
            enabled = selectedOptionId != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.5.dp, if (selectedOptionId != null) ArkText else Color.Transparent, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ArkPrimary,
                contentColor = ArkText,
                disabledContainerColor = ArkPrimary.copy(alpha = 0.4f),
                disabledContentColor = ArkText.copy(alpha = 0.4f)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Continue Investigation",
                fontFamily = Figtree,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun DetectiveSummaryContent(
    result: DetectiveResult,
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

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Investigation Summary",
                fontFamily = Sorean,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ArkText
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Case Solved heading
        Text(
            text = "✨ Case Solved! ✨",
            fontFamily = Sorean,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ArkText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Mascot Illustration (detective.png)
        Image(
            painter = painterResource(id = R.drawable.detective),
            contentDescription = "Sprout Detective",
            modifier = Modifier
                .size(90.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ArkCardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Trigger section
                Text(
                    text = "TRIGGER DISCOVERED",
                    fontFamily = Figtree,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkSecondary,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = result.triggerDiscovered,
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArkText
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = ArkSurfaceDim)
                Spacer(modifier = Modifier.height(16.dp))

                // Root Emotion section
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
                    text = result.rootEmotion,
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ArkText
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = ArkSurfaceDim)
                Spacer(modifier = Modifier.height(16.dp))

                // Recommendation section
                Text(
                    text = "RECOMMENDATION",
                    fontFamily = Figtree,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArkSecondary,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = result.recommendation,
                    fontFamily = Figtree,
                    fontSize = 13.sp,
                    color = ArkText,
                    lineHeight = 19.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Primary Button
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

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(28.dp))
    }
}
