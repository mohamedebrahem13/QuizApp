package com.example.quizapp.data.repository.local

import com.example.quizapp.data.repository.local.model.QuizQuestionEntity
import com.example.quizapp.domain.repository.local.IQuizLocalDS

class QuizLocalDS : IQuizLocalDS {
    override fun fetchQuestions(): List<QuizQuestionEntity> {
        return listOf(
            QuizQuestionEntity("What is the capital of France?", listOf("Berlin", "Paris", "Madrid", "Rome"), 1),
            QuizQuestionEntity("Which planet is known as the Red Planet?", listOf("Earth", "Venus", "Mars", "Jupiter"), 2)
        )
    }
}