package com.example.quizapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.common.ProgressBarIndicator
import com.example.quizapp.ui.viewmodels.QuestionsContract
import com.example.quizapp.ui.viewmodels.QuestionsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuestionScreen(onQuizFinished: (Int) -> Unit, viewModel: QuestionsViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    // Check if the quiz is finished
    LaunchedEffect(Unit) {
        viewModel.singleEvent.collect { onQuizFinished(state.score) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Adjust padding as needed
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        // Display Score
        Text(
            text = "Score: ${state.score}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium), // Rounded corners
            color = Color.Gray
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    count = state.questions.size,
                    state = pagerState,
                    userScrollEnabled = false,  // Disable manual scrolling
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp) // Add bottom padding to avoid overlap with indicator
                ) { page ->
                    val question = state.questions[page]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = question.question,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        question.options.forEachIndexed { index, answer ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = state.selectedOption == index,
                                    onClick = {
                                        if (state.isAnswerCorrect == null) {
                                            viewModel.onActionTrigger(QuestionsContract.QuestionsAction.SelectOption(index))
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = answer, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                                // Display Correct/Incorrect beside the selected answer
                                if (state.selectedOption == index && state.isAnswerCorrect != null) {
                                    val resultText = if (state.isAnswerCorrect == true) "Correct!" else "Incorrect!"
                                    val resultColor = if (state.isAnswerCorrect == true) Color.Green else Color.Red
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = resultText,
                                        color = resultColor,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }


                    }
                }

                ProgressBarIndicator(
                    currentPage = pagerState.currentPage,
                    totalPages = state.questions.size
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Bar with Buttons
        BottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent
        ) {
            Button(
                onClick = {
                    // Move to next question
                    viewModel.onActionTrigger(QuestionsContract.QuestionsAction.LoadNextQuestion)
                    // Move pager to next question
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = state.isAnswerCorrect != null // Ensure next is only enabled if answer was submitted
            ) {
                Text(if (pagerState.currentPage == state.questions.size - 1) "Finish" else "Next")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (state.selectedOption != -1 && state.isAnswerCorrect == null) {
                        // Submit the answer
                        viewModel.onActionTrigger(QuestionsContract.QuestionsAction.SubmitAnswer)
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = state.selectedOption != -1 && state.isAnswerCorrect == null
            ) {
                Text("Submit")
            }


        }
    }
}