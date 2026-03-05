package com.example.vaultprepssc.ui.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    val subjectStats = repository.getAllSessions()
        .map { sessions ->
            sessions.groupBy { it.id.substringBefore("_") } // Assuming ID has subject prefix
                .mapValues { entry ->
                    entry.value.average { it.score.toDouble() / it.totalQuestions }
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
        
    val totalQuestionsSolved = repository.getAllSessions()
        .map { it.sumOf { s -> s.totalQuestions } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}

fun <T> List<T>.average(selector: (T) -> Double): Double {
    if (this.isEmpty()) return 0.0
    return this.sumOf(selector) / this.size
}
