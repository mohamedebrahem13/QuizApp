package com.example.quizapp.domain.intractor

import com.example.quizapp.common.Resource
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.repository.IQuizRepository
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val quizRepository: IQuizRepository
) {

    suspend operator fun invoke(): Resource<List<QuizQuestion>> {
        return try {
            val remoteQuestions = quizRepository.getQuizQuestionsFromRemote()
            if (remoteQuestions.isNotEmpty()) {
                // Return the questions fetched from remote
                Resource.Success(remoteQuestions)
            } else {
                // Fallback to local if remote is empty
                val localQuestions = quizRepository.getQuizQuestionsFromLocal()
                Resource.Success(localQuestions)
            }
        } catch (exception: Exception) {
            // Handle any errors (network issues, data parsing, etc.)
            Resource.Error("Failed to fetch quiz questions: ${exception.localizedMessage}")
        }
    }
}