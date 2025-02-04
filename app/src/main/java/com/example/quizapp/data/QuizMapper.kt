package com.example.quizapp.data

import com.example.quizapp.common.Mapper
import com.example.quizapp.data.model.QuizQuestionDto
import com.example.quizapp.data.repository.local.room.model.QuizQuestionEntity
import com.example.quizapp.domain.model.QuizQuestion

object  QuizMapper : Mapper<QuizQuestionDto, QuizQuestion, QuizQuestionEntity>() {

    // Mapping from DTO (API) to Domain Model
    override fun dtoToDomain(model: QuizQuestionDto): QuizQuestion {
        return QuizQuestion(
            question = model.question.orEmpty(), // Default to empty string if null
            options = model.options.orEmpty(), // Default to an empty list if null
            correctAnswer = model.correctAnswer ?: 0 // Default to 0 if null
        )

    }

    override fun domainToEntity(model: QuizQuestion): QuizQuestionEntity {
        return QuizQuestionEntity(
            question = model.question,
            options = model.options,
            correctAnswer = model.correctAnswer
        )
    }

    // Mapping from Entity (Local DB) to Domain Model
    override fun entityToDomain(model: QuizQuestionEntity): QuizQuestion {
        return QuizQuestion(
            question = model.question,
            options = model.options,
            correctAnswer = model.correctAnswer
        )
    }

    // New method to map a list of DTOs to a list of Domain Models
    fun dtoListToDomainList(dtoList: List<QuizQuestionDto>): List<QuizQuestion> {
        return dtoList.map { dtoToDomain(it) } // Use the existing mapping method for each item
    }
    fun domainListToEntityList(domainList: List<QuizQuestion>): List<QuizQuestionEntity> {
        return domainList.map { domainToEntity(it) } // Use the existing mapping method for each item
    }

    // New method to map a list of Entities to a list of Domain Models
    fun entityListToDomainList(entityList: List<QuizQuestionEntity>): List<QuizQuestion> {
        return entityList.map { entityToDomain(it) } // Use the existing mapping method for each item
    }
}