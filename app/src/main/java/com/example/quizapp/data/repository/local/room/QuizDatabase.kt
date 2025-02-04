package com.example.quizapp.data.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quizapp.data.repository.local.room.converter.StringListConverter
import com.example.quizapp.data.repository.local.room.model.QuizQuestionEntity

@Database(entities = [QuizQuestionEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}