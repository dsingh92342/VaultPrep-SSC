package com.example.vaultprepssc.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.util.FocusModeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val focusModeManager: FocusModeManager
) : ViewModel() {

    val isOffline = focusModeManager.isOffline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
        
    // Premium logic would go here
}
