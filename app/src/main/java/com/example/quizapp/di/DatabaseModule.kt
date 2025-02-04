package com.example.quizapp.di

import android.content.Context
import androidx.room.Room
import com.example.quizapp.data.repository.local.room.QuizDao
import com.example.quizapp.data.repository.local.room.QuizDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuizDatabase {
        return Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            "quiz_database"
        ).build()
    }

    @Provides
    fun provideQuizDao(database: QuizDatabase): QuizDao {
        return database.quizDao()
    }
}