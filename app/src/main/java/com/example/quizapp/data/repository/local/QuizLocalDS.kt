package com.example.quizapp.data.repository.local

import com.example.quizapp.data.repository.local.room.QuizDao
import com.example.quizapp.data.repository.local.room.model.QuizQuestionEntity
import com.example.quizapp.domain.repository.local.IQuizLocalDS
import kotlinx.coroutines.delay
import javax.inject.Inject

class QuizLocalDS @Inject constructor(
    private val quizDao: QuizDao
) : IQuizLocalDS {
    override suspend fun insertQuestion(questions: List<QuizQuestionEntity>) {
        quizDao.insertQuestions(questions)
    }

    override suspend fun fetchQuestions(): List<QuizQuestionEntity> {
        delay(2000)
        return quizDao.getAllQuestions()
    }

}