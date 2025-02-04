package com.example.quizapp.domain.intractor

import com.example.quizapp.common.Resource
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.repository.IQuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val quizRepository: IQuizRepository
) {
    operator fun invoke(): Flow<Resource<List<QuizQuestion>>> = flow {
        emit(Resource.Loading)  // Emit loading state

        try {
            val remoteQuestions = quizRepository.getQuizQuestionsFromRemote()
            if (remoteQuestions.isNotEmpty()) {
                quizRepository.saveQuizQuestions(remoteQuestions)
                emit(Resource.Success(remoteQuestions))
            } else {
                val localQuestions = quizRepository.getQuizQuestionsFromLocal()

                // Corrected this condition
                if (localQuestions.isNotEmpty()) {
                    emit(Resource.Success(localQuestions))
                } else {
                    emit(Resource.Error("No quiz questions available locally or remotely."))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.Error("Failed to fetch quiz questions: ${exception.localizedMessage}"))
        }
    }
}