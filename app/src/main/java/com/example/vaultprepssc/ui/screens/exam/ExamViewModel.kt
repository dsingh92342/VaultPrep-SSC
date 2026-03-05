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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.vaultprepssc.data.local.entity.TestSession
import com.example.vaultprepssc.data.local.entity.UserAttempt
import com.example.vaultprepssc.util.MockGenerator
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val repository: QuestionRepository,
    private val mockGenerator: MockGenerator,
    private val focusModeManager: com.example.vaultprepssc.util.FocusModeManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val testId: String = checkNotNull(savedStateHandle["testId"])

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: State<Int> = _currentQuestionIndex
    
    val isFocusMode = focusModeManager.isOffline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

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
            if (testId.startsWith("mock_")) {
                _questions.value = mockGenerator.generateFullMock()
            } else {
                repository.getRandomQuestions(25).collect {
                    _questions.value = it
                }
            }
        }
    }
    
    fun submitExam(onComplete: () -> Unit) {
        viewModelScope.launch {
            // In a real app, we'd calculate score from attempts
            // For now, let's create a simulated session result
            val attempts: List<UserAttempt> = repository.getAttemptsForSession(testId).first()
            val score = attempts.count { it.isCorrect }
            
            repository.saveSession(
                TestSession(
                    id = testId,
                    testName = if (testId.startsWith("mock_")) "Full Mock Test" else "Topic Practice",
                    score = score,
                    totalQuestions = _questions.value.size,
                    timestamp = System.currentTimeMillis(),
                    isFocusModeActive = focusModeManager.isOffline.value,
                    sessionType = if (testId.startsWith("mock_")) "MOCK" else "DRILL"
                )
            )
            onComplete()
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
