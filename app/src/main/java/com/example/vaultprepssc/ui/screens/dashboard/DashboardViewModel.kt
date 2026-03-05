package com.example.vaultprepssc.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: QuestionRepository,
    private val focusModeManager: com.example.vaultprepssc.util.FocusModeManager
) : ViewModel() {

    val isOffline = focusModeManager.isOffline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val recentSessions = repository.getAllSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // In a real app, we'd calculate streaks and stats here
}
