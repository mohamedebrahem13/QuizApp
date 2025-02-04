package com.example.quizapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.viewmodels.QuestionsContract
import com.example.quizapp.ui.viewmodels.QuestionsViewModel

@Composable
fun QuestionScreen(  onQuizFinished: (Int) -> Unit, viewModel: QuestionsViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsState()

    val currentQuestion = state.currentQuestion
    val score = state.score
    val isAnswerCorrect = state.isAnswerCorrect
    val selectedOption = state.selectedOption  // Now using ViewModel state

    val isSubmitEnabled = selectedOption != -1 && isAnswerCorrect == null
    val isNextEnabled = isAnswerCorrect != null
    // Check if the quiz is finished
    LaunchedEffect(state.isQuizFinished) {
        if (state.isQuizFinished) {
            onQuizFinished(state.score)
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar ( modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent){
                Button(
                    onClick = {
                        viewModel.onActionTrigger(QuestionsContract.QuestionsAction.LoadNextQuestion)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = isNextEnabled
                ) {
                    Text("Next")
                }
                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (isSubmitEnabled) {
                            viewModel.onActionTrigger(QuestionsContract.QuestionsAction.SubmitAnswer)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = isSubmitEnabled
                ) {
                    Text("Submit")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Display Score
            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display Current Question
            currentQuestion?.let { question ->
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display Answers in RadioGroup vertically
                Column {
                    question.options.forEachIndexed { index, answer ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == index,
                                onClick = { if (isAnswerCorrect == null) {
                                    viewModel.onActionTrigger(QuestionsContract.QuestionsAction.SelectOption(index))
                                } }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = answer, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                // Show if the answer is correct or incorrect
                isAnswerCorrect?.let { correct ->
                    val resultText = if (correct) "Correct!" else "Incorrect!"
                    val resultColor = if (correct) Color.Green else Color.Red
                    Text(
                        text = resultText,
                        color = resultColor,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}
