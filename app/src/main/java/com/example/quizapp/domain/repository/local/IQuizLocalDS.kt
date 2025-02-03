package com.example.quizapp.domain.repository.local

import com.example.quizapp.data.repository.local.model.QuizQuestionEntity

interface IQuizLocalDS {
    fun fetchQuestions(): List<QuizQuestionEntity>

}