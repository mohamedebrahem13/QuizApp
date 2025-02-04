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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            // Show Loading Indicator
            CircularProgressIndicator()
        } else {
            // Show Quiz Content when not loading
            Column(
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
                    color = Color(0xFF03DAC5)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        HorizontalPager(
                            count = state.questions.size,
                            state = pagerState,
                            userScrollEnabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
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
                                    val isSelected = state.selectedOption == index
                                    val isCorrectAnswer = state.isAnswerCorrect == true && isSelected
                                    val isIncorrectAnswer = state.isAnswerCorrect == false && isSelected

                                    val radioButtonColor = when {
                                        isCorrectAnswer -> Color.Green
                                        isIncorrectAnswer -> Color.Red
                                        else -> Color.White  // Default color
                                    }
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
                                            }, colors = RadioButtonDefaults.colors(
                                                    selectedColor = radioButtonColor,
                                            unselectedColor = Color.White  // Color for unselected options
                                        )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = answer,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.White
                                        )


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
                    // Next/Finish Button
                    Button(
                        onClick = {
                            viewModel.onActionTrigger(QuestionsContract.QuestionsAction.LoadNextQuestion)
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = state.isAnswerCorrect != null,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
                    ) {
                        Text(
                            if (pagerState.currentPage == state.questions.size - 1) "Finish" else "Next",
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            if (state.selectedOption != -1 && state.isAnswerCorrect == null) {
                                viewModel.onActionTrigger(QuestionsContract.QuestionsAction.SubmitAnswer)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = state.selectedOption != -1 && state.isAnswerCorrect == null,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
                    ) {
                        Text("Submit", color = Color.Black)
                    }
                }
            }
        }
    }
}