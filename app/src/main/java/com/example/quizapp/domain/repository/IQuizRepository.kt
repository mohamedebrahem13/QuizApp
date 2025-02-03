package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.QuizQuestion

// domain/repository/QuizRepository.kt
interface IQuizRepository {
    suspend fun getQuizQuestionsFromRemote(): List<QuizQuestion>
    suspend fun getQuizQuestionsFromLocal(): List<QuizQuestion>
}