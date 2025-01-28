package com.example.hemanglabproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hemanglabproject.databinding.ActivityMainBinding

data class Question(val textResId: Int, val answer: Boolean)

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var correctAnswers = 0  // Track correct answers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial question
        updateQuestion()

        // Set click listeners for True/False
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            disableChoices()
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            disableChoices()
        }

        // Set click listener for Next button
        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()

            // Enable buttons when moving to the next question
            enableChoices()

            // If the user has answered all questions, show the score
            if (currentIndex == 0) {
                showScore()
                // Reset the score for the next round
                resetScore()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

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
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun enableChoices() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun showScore() {
        val totalQuestions = questionBank.size
        val scorePercentage = (correctAnswers.toDouble() / totalQuestions) * 100
        val scoreMessage = "Your score: ${scorePercentage.toInt()}%"
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
    }

    private fun resetScore() {
        correctAnswers = 0  // reset score for the next round
    }
}
