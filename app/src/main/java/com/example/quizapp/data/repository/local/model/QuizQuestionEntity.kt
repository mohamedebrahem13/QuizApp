package com.example.quizapp.data.repository.local.model

data class QuizQuestionEntity(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)