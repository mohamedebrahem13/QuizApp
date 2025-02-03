package com.example.quizapp.data.repository.remote

import com.example.quizapp.data.model.QuizQuestionDto
import com.example.quizapp.domain.repository.remote.IQuizRemoteDS

class QuizRemoteDS : IQuizRemoteDS {
    override fun fetchQuestions(): List<QuizQuestionDto> {
        return listOf(
            QuizQuestionDto("What is the capital of France?", listOf("Berlin", "Paris", "Madrid", "Rome"), 1),
            QuizQuestionDto("Which planet is known as the Red Planet?", listOf("Earth", "Venus", "Mars", "Jupiter"), 2)
        )
    }
}