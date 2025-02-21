package com.example.hemanglabproject

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

data class Question(val textResId: Int, val answer: Boolean)

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val TAG = "QuizViewModel"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // Use savedStateHandle to persist the current index
    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) {
            savedStateHandle[CURRENT_INDEX_KEY] = value
        }


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var isCheater: Boolean
        get() = savedStateHandle[IS_CHEATER_KEY] ?: false
        set(value) {
            savedStateHandle[IS_CHEATER_KEY] = value
        }

    fun markCheated() {
        cheatedQuestions[currentIndex] = true
    }


    private val cheatedQuestions = mutableMapOf<Int, Boolean>()


    fun isQuestionCheated(index: Int): Boolean {
        return cheatedQuestions.getOrDefault(index, false)
    }



    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        Log.d(TAG, "Moving to next question")
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        Log.d(TAG, "Moving to previous question")
        currentIndex = if (currentIndex - 1 < 0) questionBank.size - 1 else currentIndex - 1
    }
}
