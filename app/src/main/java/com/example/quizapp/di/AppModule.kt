package com.example.quizapp.di

import com.example.quizapp.data.QuizMapper
import com.example.quizapp.data.repository.QuizRepositoryImpl
import com.example.quizapp.data.repository.local.QuizLocalDS
import com.example.quizapp.data.repository.local.room.QuizDao
import com.example.quizapp.data.repository.remote.QuizRemoteDS
import com.example.quizapp.domain.intractor.GetQuizQuestionsUseCase
import com.example.quizapp.domain.repository.IQuizRepository
import com.example.quizapp.domain.repository.local.IQuizLocalDS
import com.example.quizapp.domain.repository.remote.IQuizRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideQuizDataSource(): IQuizRemoteDS = QuizRemoteDS()

    // Provide Local Data Source with DAO injection
    @Provides
    fun provideQuizLocalDataSource(quizDao: QuizDao): IQuizLocalDS = QuizLocalDS(quizDao)

    // Provide QuizRepository
    @Provides
    fun provideQuizRepository(remoteDataSource: IQuizRemoteDS,localDataSource: IQuizLocalDS,mapper: QuizMapper): IQuizRepository =
        QuizRepositoryImpl(remoteDataSource,localDataSource,mapper)

    @Provides
    fun provideQuizMapper(): QuizMapper = QuizMapper // Since it's an object, no need to instantiate


    @Provides
    fun provideGetQuizQuestionsUseCase(
        quizRepository: IQuizRepository
    ): GetQuizQuestionsUseCase {
        return GetQuizQuestionsUseCase(quizRepository)
    }
}