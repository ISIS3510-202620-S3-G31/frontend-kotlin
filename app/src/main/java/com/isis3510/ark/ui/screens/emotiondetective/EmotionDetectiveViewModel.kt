package com.isis3510.ark.ui.screens.emotiondetective

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EmotionDetectiveViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        EmotionDetectiveState(
            questions = defaultQuestions()
        )
    )
    val state: StateFlow<EmotionDetectiveState> = _state.asStateFlow()

    fun startInvestigation() {
        _state.update {
            it.copy(
                currentStep = DetectiveStep.QUESTIONS,
                currentQuestionIndex = 0,
                selectedAnswers = emptyMap(),
                result = null,
                isSaved = false
            )
        }
    }

    fun selectOption(questionId: Int, optionId: Int) {
        _state.update { current ->
            val updated = current.selectedAnswers.toMutableMap()
            updated[questionId] = optionId
            current.copy(selectedAnswers = updated)
        }
    }

    fun nextQuestion() {
        val current = _state.value
        val questions = current.questions

        if (current.currentQuestionIndex < questions.size - 1) {
            _state.update { it.copy(currentQuestionIndex = it.currentQuestionIndex + 1) }
        } else {
            // Last question completed -> analyze answers and produce result
            val result = analyzeAnswers(current.selectedAnswers)
            _state.update {
                it.copy(
                    currentStep = DetectiveStep.SUMMARY,
                    result = result
                )
            }
        }
    }

    fun previousQuestion() {
        val current = _state.value
        if (current.currentQuestionIndex > 0) {
            _state.update { it.copy(currentQuestionIndex = it.currentQuestionIndex - 1) }
        } else {
            _state.update { it.copy(currentStep = DetectiveStep.INTRO) }
        }
    }

    fun saveToInsights() {
        _state.update { it.copy(isSaved = true) }
    }

    fun resetInvestigation() {
        _state.update {
            EmotionDetectiveState(
                questions = defaultQuestions()
            )
        }
    }

    private fun analyzeAnswers(answers: Map<Int, Int>): DetectiveResult {
        val trigger = when (answers[1]) {
            1 -> "Fear of falling behind in deadlines"
            2 -> "Argument or misunderstanding with a friend"
            3 -> "Too many small responsibilities piling up"
            else -> "Unexpected situation or external pressure"
        }

        val rootEmotion = when (answers[2]) {
            1 -> "Overwhelm & Anxiety"
            2 -> "Suppressed worry & somatic tension"
            3 -> "Frustration & physical fatigue"
            else -> "Restlessness without clear anchor"
        }

        val recommendation = when (answers[4]) {
            1 -> "Take 2 minutes to try Custom Interval Breathing or leave a note in the Achievement Jar."
            2 -> "Open the Scream Tank to release accumulated pressure safely."
            3 -> "Practice Somatic Body Mapping to relax your shoulders and chest."
            else -> "Give yourself permission to pause, take three deep breaths, and hydrate."
        }

        return DetectiveResult(
            triggerDiscovered = trigger,
            rootEmotion = rootEmotion,
            recommendation = recommendation
        )
    }

    private fun defaultQuestions(): List<DetectiveQuestion> {
        return listOf(
            DetectiveQuestion(
                id = 1,
                clueNumber = 1,
                totalClues = 4,
                questionText = "What happened right before you felt overwhelmed?",
                options = listOf(
                    DetectiveOption(1, "A tough test or deadline at school/work"),
                    DetectiveOption(2, "An argument or misunderstanding with a friend"),
                    DetectiveOption(3, "Too many small things piled up"),
                    DetectiveOption(4, "Something else happened...")
                )
            ),
            DetectiveQuestion(
                id = 2,
                clueNumber = 2,
                totalClues = 4,
                questionText = "Where in your body did you first notice the discomfort?",
                options = listOf(
                    DetectiveOption(1, "Tightness in chest and shallow breathing"),
                    DetectiveOption(2, "A heavy knot in the stomach or gut"),
                    DetectiveOption(3, "Tension locked in shoulders and neck"),
                    DetectiveOption(4, "Racing thoughts with physical stillness")
                )
            ),
            DetectiveQuestion(
                id = 3,
                clueNumber = 3,
                totalClues = 4,
                questionText = "How long has this feeling been with you today?",
                options = listOf(
                    DetectiveOption(1, "It just happened in the last hour"),
                    DetectiveOption(2, "It has been lingering for most of the day"),
                    DetectiveOption(3, "It started yesterday or a few days ago"),
                    DetectiveOption(4, "I cannot pinpoint exactly when it started")
                )
            ),
            DetectiveQuestion(
                id = 4,
                clueNumber = 4,
                totalClues = 4,
                questionText = "What would bring you the most comfort right now?",
                options = listOf(
                    DetectiveOption(1, "A slow breathing exercise to calm down"),
                    DetectiveOption(2, "A safe place to vent and release anger"),
                    DetectiveOption(3, "Releasing physical tension from my body"),
                    DetectiveOption(4, "A gentle reminder that I am doing my best")
                )
            )
        )
    }
}
