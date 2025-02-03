package com.example.quizapp.data.repository

import com.example.quizapp.data.QuizMapper
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.repository.IQuizRepository
import com.example.quizapp.domain.repository.local.IQuizLocalDS
import com.example.quizapp.domain.repository.remote.IQuizRemoteDS


class QuizRepositoryImpl(
    private val remoteDataSource: IQuizRemoteDS, // remote source (e.g., Retrofit)
    private val localDataSource: IQuizLocalDS, //  local source (e.g., Room)
    private val quizQuestionMapper: QuizMapper // mapper (e.g., QuizQuestionMapper
) : IQuizRepository {

    override suspend fun getQuizQuestionsFromRemote(): List<QuizQuestion> {
        return QuizMapper.dtoListToDomainList(remoteDataSource.fetchQuestions()) // Convert to domain model
    }

    override suspend fun getQuizQuestionsFromLocal(): List<QuizQuestion> {
        // Fetch quiz questions from the local source (e.g., Room)
        return quizQuestionMapper.entityListToDomainList(localDataSource.fetchQuestions()) // Convert to domain model}
    }
}
