package com.example.quizapp.data.repository.remote

import com.example.quizapp.data.model.QuizQuestionDto
import com.example.quizapp.domain.repository.remote.IQuizRemoteDS
import kotlinx.coroutines.delay

class QuizRemoteDS : IQuizRemoteDS {
    override suspend fun fetchQuestions(): List<QuizQuestionDto> {
        delay(2000)
        return listOf(
            QuizQuestionDto("What is the capital of France?", listOf("Berlin", "Paris", "Madrid", "Rome"), 1),
            QuizQuestionDto("Which planet is known as the Red Planet?", listOf("Earth", "Venus", "Mars", "Jupiter"), 2),
            QuizQuestionDto("Who wrote 'Romeo and Juliet'?", listOf("Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"), 1),
            QuizQuestionDto("What is the largest ocean on Earth?", listOf("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"), 3),
            QuizQuestionDto("What is the chemical symbol for Gold?", listOf("Au", "Ag", "Fe", "Pb"), 0),
            QuizQuestionDto("Who painted the Mona Lisa?", listOf("Vincent Van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"), 2),
            QuizQuestionDto("Which country hosted the 2016 Summer Olympics?", listOf("China", "Brazil", "United Kingdom", "Russia"), 1),
            QuizQuestionDto("What is the hardest natural substance on Earth?", listOf("Gold", "Iron", "Diamond", "Platinum"), 2),
            QuizQuestionDto("What gas do plants absorb from the atmosphere?", listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"), 1),
            QuizQuestionDto("Which is the smallest continent by land area?", listOf("Europe", "Australia", "Antarctica", "South America"), 1),
            QuizQuestionDto("In what year did the Titanic sink?", listOf("1912", "1905", "1920", "1918"), 0),
            QuizQuestionDto("What is the currency of Japan?", listOf("Yuan", "Won", "Yen", "Ringgit"), 2),
            QuizQuestionDto("Who discovered penicillin?", listOf("Marie Curie", "Alexander Fleming", "Isaac Newton", "Albert Einstein"), 1),
            QuizQuestionDto("What is the tallest mountain in the world?", listOf("K2", "Mount Everest", "Kangchenjunga", "Lhotse"), 1),
            QuizQuestionDto("Which element has the atomic number 1?", listOf("Helium", "Hydrogen", "Oxygen", "Nitrogen"), 1)
        )
    }
}