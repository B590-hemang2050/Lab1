package com.example.hemanglabproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.hemanglabproject.QuizViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.util.Log
import org.mockito.MockedStatic
import org.mockito.Mockito.*

class QuizViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()  // Ensures LiveData runs synchronously in tests

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var mockedStatic: MockedStatic<Log>  // For mocking Log class

    @Before
    fun setUp() {
        // Create a SavedStateHandle to simulate the saved state of the ViewModel
        val savedStateHandle = SavedStateHandle()
        quizViewModel = QuizViewModel(savedStateHandle)

        // Mock static methods for Log
        mockedStatic = mockStatic(Log::class.java)
        mockedStatic.use {
            `when`(Log.d(anyString(), anyString())).thenReturn(0) // Mock Log.d to return 0
        }
    }

    @Test
    fun `test currentQuestionAnswer for true question`() {
        // Move to a question where the answer is true (e.g., Australia)
        quizViewModel.moveToNext()

        // Check if the current answer is true
        assertTrue("Expected current answer to be true", quizViewModel.currentQuestionAnswer)
    }

    @Test
    fun `test currentQuestionAnswer for false question`() {
        // Move to a question where the answer is false (e.g., Africa)
        quizViewModel.moveToNext()
        quizViewModel.moveToNext()  // Skip to the next question

        // Check if the current answer is false
        assertFalse("Expected current answer to be false", quizViewModel.currentQuestionAnswer)
    }

    @Test
    fun testCurrentQuestionAnswerForTrue() {
        // Test code
        Log.d("TAG", "Message") // This is mocked and should not cause any issues
    }

    @Before
    fun tearDown() {
        // Close the static mocking when the test is done
        mockedStatic.close()
    }
}
