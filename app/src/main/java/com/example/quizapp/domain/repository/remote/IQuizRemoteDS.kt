package com.example.quizapp.domain.repository.remote

import com.example.quizapp.data.model.QuizQuestionDto

interface IQuizRemoteDS {
    fun fetchQuestions(): List<QuizQuestionDto>
}