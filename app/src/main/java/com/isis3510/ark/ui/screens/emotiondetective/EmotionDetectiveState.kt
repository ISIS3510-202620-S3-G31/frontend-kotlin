package com.isis3510.ark.ui.screens.emotiondetective

data class DetectiveOption(
    val id: Int,
    val text: String
)

data class DetectiveQuestion(
    val id: Int,
    val clueNumber: Int,
    val totalClues: Int,
    val questionText: String,
    val options: List<DetectiveOption>
)

data class DetectiveResult(
    val triggerDiscovered: String,
    val rootEmotion: String,
    val recommendation: String
)

enum class DetectiveStep {
    INTRO,
    QUESTIONS,
    SUMMARY
}

data class EmotionDetectiveState(
    val currentStep: DetectiveStep = DetectiveStep.INTRO,
    val currentQuestionIndex: Int = 0,
    val questions: List<DetectiveQuestion> = emptyList(),
    val selectedAnswers: Map<Int, Int> = emptyMap(),
    val result: DetectiveResult? = null,
    val isSaved: Boolean = false
)
