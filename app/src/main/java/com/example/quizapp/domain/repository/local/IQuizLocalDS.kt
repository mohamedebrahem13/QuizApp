package com.example.quizapp.domain.repository.local

import com.example.quizapp.data.repository.local.room.model.QuizQuestionEntity

interface IQuizLocalDS {
    suspend fun insertQuestion(questions: List<QuizQuestionEntity>)
    suspend fun fetchQuestions(): List<QuizQuestionEntity>
}