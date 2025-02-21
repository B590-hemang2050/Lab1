package com.example.hemanglabproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hemanglabproject.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "iu.b590.spring2025.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "iu.b590.spring2025.practicum.answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding

    private var answerIsTrue = false
    private var isAnswerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the answer extra
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        // Restore the cheating state if available
        if (savedInstanceState != null) {
            isAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN, false)
            if (isAnswerShown) {
                showAnswer()
                setAnswerShownResult(true)
            }
        }

        binding.showAnswerButton.setOnClickListener {
            showAnswer()
            setAnswerShownResult(true)
        }
    }

    private fun showAnswer() {
        val answerText = if (answerIsTrue) R.string.true_button else R.string.false_button
        binding.answerTextView.setText(answerText)
        isAnswerShown = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_ANSWER_SHOWN, isAnswerShown)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
