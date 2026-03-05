package com.example.vaultprepssc.ui.screens.mistakes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.local.entity.UserAttempt
import com.example.vaultprepssc.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MistakeVaultViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _mistakeQuestions = MutableStateFlow<List<Question>>(emptyList())
    val mistakeQuestions = _mistakeQuestions.asStateFlow()

    init {
        loadMistakes()
    }

    private fun loadMistakes() {
        viewModelScope.launch {
            // Get all incorrect attempts, then fetch the corresponding questions
            repository.getMistakes().collect { attempts ->
                val questionIds = attempts.map { it.questionId }.distinct()
                val questions = mutableListOf<Question>()
                questionIds.forEach { id ->
                    repository.getQuestionById(id)?.let { questions.add(it) }
                }
                _mistakeQuestions.value = questions
            }
        }
    }
}
