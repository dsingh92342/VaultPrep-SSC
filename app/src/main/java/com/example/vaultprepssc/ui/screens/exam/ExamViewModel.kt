package com.example.vaultprepssc.ui.screens.exam
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.vaultprepssc.data.local.entity.UserAttempt
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val repository: QuestionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val testId: String = checkNotNull(savedStateHandle["testId"])

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: State<Int> = _currentQuestionIndex

    private val _timerSeconds = MutableStateFlow(3600L) // 60 minutes default
    val timerSeconds = _timerSeconds.asStateFlow()

    private var timerJob: Job? = null

    init {
        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timerSeconds.value = (_timerSeconds.value - 1).coerceAtLeast(0)
            }
        }
    }

    private val _selectedOption = mutableStateOf<Int?>(null)
    val selectedOption: State<Int?> = _selectedOption

    private fun loadQuestions() {
        viewModelScope.launch {
            repository.getRandomQuestions(100).collect {
                _questions.value = it
            }
        }
    }

    fun selectOption(index: Int) {
        _selectedOption.value = index
        val currentQ = _questions.value.getOrNull(_currentQuestionIndex.value) ?: return
        
        viewModelScope.launch {
            repository.saveAttempt(
                UserAttempt(
                    questionId = currentQ.id,
                    sessionId = testId,
                    selectedIdx = index,
                    isCorrect = index == currentQ.correctIdx,
                    timeSpent = 0 // Would ideally track this per question
                )
            )
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
            _selectedOption.value = null // Reset for next Q
        }
    }

    fun prevQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
            _selectedOption.value = null // In a real app, we'd fetch the previous answer
        }
    }

    fun jumpToQuestion(index: Int) {
        if (index in 0 until _questions.value.size) {
            _currentQuestionIndex.value = index
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
