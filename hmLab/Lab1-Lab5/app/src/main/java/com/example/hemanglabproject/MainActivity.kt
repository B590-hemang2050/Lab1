package com.example.hemanglabproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hemanglabproject.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var correctAnswers = 0  // Track correct answers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // Set initial question
        updateQuestion()

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        // Set click listeners for True/False buttons
        binding.trueButton?.setOnClickListener {
            checkAnswer(true)
            disableChoices()
        }

        binding.falseButton?.setOnClickListener {
            checkAnswer(false)
            disableChoices()
        }

        // Set click listener for Next button
        binding.nextButton?.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            enableChoices()
        }

        binding.prevButton?.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
            enableChoices()
        }
    }

    private fun updateQuestion() {
        // Fetch the question text resource ID and update the TextView
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        // Update the score if the answer is correct
        if (userAnswer == correctAnswer) {
            correctAnswers++
        }

        // Display correct/incorrect message
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun disableChoices() {
        // Disable both the True and False buttons after an answer is selected
        binding.trueButton?.isEnabled = false
        binding.falseButton?.isEnabled = false
    }

    private fun enableChoices() {
        // Enable both the True and False buttons when moving to the next question
        binding.trueButton?.isEnabled = true
        binding.falseButton?.isEnabled = true
    }
}
