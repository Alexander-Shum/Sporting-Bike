package com.example.sportingbike.data.local.repositories

import android.content.SharedPreferences
import android.util.Log
import com.example.sportingbike.data.local.models.Question
import com.example.sportingbike.utils.Constants

class QuestionRepository(private val sharedPreferences: SharedPreferences) {
    private val questions = mutableListOf<Question>(
        Question("What brand of bike does not exist?", listOf("BMW", "Jamis", "Uplift"), 2),
        Question(
            "On which side can a bicycle ride on the roadway?",
            listOf("Left", "On right", "Any"),
            1
        ),
        Question(
            "What is another name for a rear derailleur holder on a mountain or semi-mountain bike?",
            listOf("Rooster", "Parrot", "Suslik"),
            0
        ),
        Question(
            "Which part does not apply to a bicycle?",
            listOf("Stem", "Carriage", "ball pin"),
            2
        ),
        Question(
            "What is the name of the style of riding in which the bikes are more jumped than ridden?",
            listOf("Trial", "Cross", "Freeride"),
            0
        ),
        Question(
            "What is cycling called?",
            listOf("Willy", "Tailwhip", "Gout"),
            0
        ),
        Question(
            "What year was the bicycle invented?",
            listOf("In 1797", "In 1817", "In 1901"),
            1
        ),
        Question(
            "Who invented the bicycle?",
            listOf("Carl Drez", "Karl Benz", "Thomas Edi"),
            0
        )
    )

    private var currentQuestionIndex = -1

    fun getNextQuestion(): Question? {
        currentQuestionIndex++
        Log.d("TagCurrent", currentQuestionIndex.toString())
        return if (currentQuestionIndex < questions.size) questions[currentQuestionIndex]
        else null
    }

    fun clear() {
        currentQuestionIndex = -1
    }

    fun saveRecord(record: Int?) {
        val rec = record as Int
        if (!sharedPreferences.contains(Constants.SCORE_QUIZ))
            sharedPreferences.edit().putInt(Constants.SCORE_QUIZ, rec).apply()
        else {
            if (rec >= loadRecord()) {
                sharedPreferences.edit().putInt(Constants.SCORE_QUIZ, rec).apply()
            }
        }
    }

    fun loadRecord(): Int {
        return sharedPreferences.getInt(Constants.SCORE_QUIZ, 0)
    }
}