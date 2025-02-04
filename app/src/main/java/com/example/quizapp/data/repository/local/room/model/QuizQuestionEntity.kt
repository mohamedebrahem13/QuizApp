package com.example.quizapp.data.repository.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.quizapp.data.repository.local.room.converter.StringListConverter

@Entity(tableName = "quiz_questions")
@TypeConverters(StringListConverter::class)
data class QuizQuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)