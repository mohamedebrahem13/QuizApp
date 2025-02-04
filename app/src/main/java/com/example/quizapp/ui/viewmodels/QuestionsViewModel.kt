package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.quizapp.common.Resource
import com.example.quizapp.common.viewmodel.QuizViewModel
import com.example.quizapp.common.viewmodel.ViewAction
import com.example.quizapp.domain.intractor.GetQuizQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor ( private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase): QuizViewModel<QuestionsContract.QuestionsAction, QuestionsContract.QuestionsEvent,
        QuestionsContract.QuestionsViewState>(QuestionsContract.QuestionsViewState.initial()) {
    override fun clearState() {
        setState(QuestionsContract.QuestionsViewState.initial())
    }

    init {
        loadQuestions()  // Load questions when the ViewModel is initialized
    }
    // Submit an answer and update the state accordingly
    private fun submitAnswer() {
        val currentQuestion = oldViewState.currentQuestion ?: return
        val selectedIndex = oldViewState.selectedOption

        if (selectedIndex == currentQuestion.correctAnswer) {
            // Correct answer: update score
            setState(oldViewState.copy(
                score = oldViewState.score + 1,
                isAnswerCorrect = true
            ))
        } else {
            // Incorrect answer
            setState(oldViewState.copy(
                isAnswerCorrect = false
            ))
        }
    }
    private fun onOptionSelected(index: Int) {
        setState(oldViewState.copy(selectedOption = index)) }


    // Move to the next question when the user triggers the action
    private fun moveToNextQuestion() {
        val nextIndex = oldViewState.currentQuestionIndex + 1
        if (nextIndex < oldViewState.totalQuestions) {
            // Update the state with the next question
            setState(oldViewState.copy(
                currentQuestion = oldViewState.questions[nextIndex],
                currentQuestionIndex = nextIndex,
                isAnswerCorrect = null // Reset the answer status for the next question
                , selectedOption = -1 // Reset the selected option
            ))
        } else {
            sendEvent(QuestionsContract.QuestionsEvent.QuizFinished)
        }
    }

    // This method is triggered when any action occurs
    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is QuestionsContract.QuestionsAction.LoadNextQuestion -> {
                    // Move to the next question
                    moveToNextQuestion()
            }
            is QuestionsContract.QuestionsAction.SubmitAnswer -> {
                submitAnswer() // Handle the answer submission
            }
            is QuestionsContract.QuestionsAction.SelectOption -> {
                onOptionSelected(action.selectedOption)
            }
        }
    }

    // This function loads the quiz questions
    private fun loadQuestions() {
        // Call the UseCase to get the questions
        viewModelScope.launch {
            getQuizQuestionsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // On success, update the state with the quiz questions
                        setState(oldViewState.copy(
                            totalQuestions = result.data.size,
                            currentQuestion = result.data.firstOrNull(),  // Using firstOrNull() to prevent crashes if the list is empty
                            questions = result.data,
                            isLoading = false
                        ))
                    }
                    is Resource.Error -> {
                        // Handle errors (e.g., show a toast or an error message)
                        setState(oldViewState.copy(
                            isLoading = false,
                            exception = result.message
                        ))
                    }
                    is Resource.Loading -> {
                        // Update state to indicate loading
                        setState(oldViewState.copy(isLoading = true))
                    }
                }
            }
        }
    }
}
