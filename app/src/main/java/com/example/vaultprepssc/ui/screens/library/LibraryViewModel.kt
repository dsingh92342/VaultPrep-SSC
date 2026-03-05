package com.example.vaultprepssc.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedSubject = MutableStateFlow<String?>(null)
    val selectedSubject = _selectedSubject.asStateFlow()

    val questions = combine(
        repository.getAllQuestions(),
        _searchQuery,
        _selectedSubject
    ) { allQuestions, query, subject ->
        allQuestions.filter { question ->
            val matchesQuery = question.text.contains(query, ignoreCase = true) ||
                    question.topicId.contains(query, ignoreCase = true)
            val matchesSubject = subject == null || question.subjectId == subject
            matchesQuery && matchesSubject
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectSubject(subject: String?) {
        _selectedSubject.value = subject
    }
}
