package com.example.hemanglabproject

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hemanglabproject.databinding.ActivityMainBinding
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val cheated = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            if (cheated) {
                quizViewModel.markCheated()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        updateQuestion()

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatButton?.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        binding.trueButton?.setOnClickListener {
            checkAnswer(true)
            disableChoices()
        }

        binding.falseButton?.setOnClickListener {
            checkAnswer(false)
            disableChoices()
        }

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
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer: Boolean = quizViewModel.currentQuestionAnswer
        val currentIndex = quizViewModel.currentIndex

        val messageResId = when {
            quizViewModel.isQuestionCheated(currentIndex) -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun disableChoices() {
        binding.trueButton?.isEnabled = false
        binding.falseButton?.isEnabled = false
    }

    private fun enableChoices() {
        binding.trueButton?.isEnabled = true
        binding.falseButton?.isEnabled = true
    }
}
