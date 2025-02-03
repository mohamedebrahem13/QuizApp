package com.example.quizapp.domain.model

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)
