package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quizapp.ui.screen.FinishedScreen
import com.example.quizapp.ui.screen.QuestionScreen
import com.example.quizapp.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                AppNavHost(innerPadding = PaddingValues(16.dp))
            }
        }
    }
    @Composable
    fun AppNavHost(innerPadding: PaddingValues) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Question, modifier = Modifier.padding(innerPadding)) {
            composable<Question>{
                QuestionScreen(onQuizFinished = { score ->
                    navController.navigate(Finish(score))
                })

            }
            composable<Finish> { backStackEntry ->
                val score = backStackEntry.toRoute<Finish>()
                FinishedScreen(score.score)
            }
        }
    }

    @Serializable
    data object Question

    @Serializable
    data class Finish (val score: Int)
}

