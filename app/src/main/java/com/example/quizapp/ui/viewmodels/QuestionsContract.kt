package com.example.quizapp.ui.viewmodels

import com.example.quizapp.common.viewmodel.ViewAction
import com.example.quizapp.common.viewmodel.ViewEvent
import com.example.quizapp.common.viewmodel.ViewState
import com.example.quizapp.domain.model.QuizQuestion

interface QuestionsContract {

    // Actions: User actions that trigger state changes
    sealed class QuestionsAction : ViewAction {
        data class SelectOption(val selectedOption: Int) : QuestionsAction()  // New Action
        data object LoadNextQuestion : QuestionsAction() // Trigger loading next question
        data object SubmitAnswer : QuestionsAction() // Submit an answer
    }

    // Events: Events triggered based on logic like whether the answer is correct or wrong
    sealed class QuestionsEvent : ViewEvent {
        data object CorrectAnswer : QuestionsEvent() // The user's answer was correct
        data object IncorrectAnswer : QuestionsEvent() // The user's answer was incorrect
        data object QuizFinished : QuestionsEvent() // The quiz is finished
    }

    // View State: Holds the UI state data
    data class QuestionsViewState(

        val questions: List<QuizQuestion> = emptyList(), // List of quiz questions
        val isLoading: Boolean = false, // To show loading indicator
        val currentQuestion: QuizQuestion? = null, // The current question being shown
        val score: Int = 0, // The current score (number of correct answers)
        val currentQuestionIndex: Int = 0, // Track current question index
        val totalQuestions: Int = 0, // Total number of questions in the quiz
        val isAnswerCorrect: Boolean? = null, // Indicates if the last answer was correct
        val isQuizFinished: Boolean = false, // To check if the quiz is completed
        val exception: String? = null,
        val selectedOption: Int = -1  // Added this line
    ) : ViewState {
        companion object {
            fun initial() = QuestionsViewState()
        }
    }
}