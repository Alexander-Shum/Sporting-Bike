package com.example.sportingbike.ui.fragment.quiz

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportingbike.R
import com.example.sportingbike.data.local.repositories.QuestionRepository
import com.example.sportingbike.data.local.models.Question

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val questionRepository = QuestionRepository(sharedPreferences)

    private val _currentQuestion = MutableLiveData<Question?>()
    val currentQuestion: LiveData<Question?>
        get() = _currentQuestion

    private val _answers = MutableLiveData<List<String>?>()
    val answers: LiveData<List<String>?>
        get() = _answers

    private val _selectedAnswer = MutableLiveData<Int?>()
    val selectedAnswer: LiveData<Int?>
        get() = _selectedAnswer

    private val _isAnswerCorrect = MutableLiveData<Boolean?>()
    val answerCorrect: LiveData<Boolean?>
        get() = _isAnswerCorrect

    private val _answer1BackgroundColor = MutableLiveData<ColorStateList?>()
    val answer1BackgroundColor: LiveData<ColorStateList?>
        get() = _answer1BackgroundColor

    private val _answer2BackgroundColor = MutableLiveData<ColorStateList?>()
    val answer2BackgroundColor: LiveData<ColorStateList?>
        get() = _answer2BackgroundColor

    private val _answer3BackgroundColor = MutableLiveData<ColorStateList?>()
    val answer3BackgroundColor: LiveData<ColorStateList?>
        get() = _answer3BackgroundColor

    var score = MutableLiveData(0)
    var highScore = MutableLiveData(0)

    init {
        getNextQuestion()
    }

    private val _isGameFinished = MutableLiveData<Boolean>()
    val isGameFinished: LiveData<Boolean>
        get() = _isGameFinished

    fun onAnswerSelected(index: Int, context: Context) {
        _selectedAnswer.value = index
        val isCorrect = index == _currentQuestion.value?.correctAnswerIndex
        _isAnswerCorrect.value = isCorrect

        val colorResId = if (isCorrect) R.color.green else R.color.red
        if (isCorrect) {
            score.value = (score.value ?: 0) + 1
        }

        val colorStateList = ContextCompat.getColorStateList(context, colorResId)

        when (index) {
            0 -> _answer1BackgroundColor.value = colorStateList
            1 -> _answer2BackgroundColor.value = colorStateList
            2 -> _answer3BackgroundColor.value = colorStateList
        }
    }

    fun onNextQuestion() {
        _currentQuestion.value = null
        _isAnswerCorrect.value = null
        _selectedAnswer.value = null
        _answer1BackgroundColor.value = null
        _answer2BackgroundColor.value = null
        _answer3BackgroundColor.value = null

        getNextQuestion()
    }

    private fun getNextQuestion() {
        val question = questionRepository.getNextQuestion()
        if (question != null) {
            Log.d("TagQues" , question.text)
            _currentQuestion.value = question!!
            _answers.value = question!!.answers
            Log.d("TagQues" , question.text)
        } else {
            _isGameFinished.value = true
        }
    }

    fun saveData(){
        questionRepository.saveRecord(score.value)
    }

    fun loadData(){
        highScore.value = questionRepository.loadRecord()
    }

    fun reset() {
        questionRepository.clear()
        _currentQuestion.value = null
        _answers.value = null
        _selectedAnswer.value = null
        _isAnswerCorrect.value = null
        _answer1BackgroundColor.value = null
        _answer2BackgroundColor.value = null
        _answer3BackgroundColor.value = null
        score.value = 0
        _isGameFinished.value = false
        getNextQuestion()
    }
}