package com.example.quizapp.data.model

data class QuizQuestionDto(
    val question: String?,
    val options: List<String>?,
    val correctAnswer: Int?
)