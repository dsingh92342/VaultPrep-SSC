package com.example.vaultprepssc.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaultprepssc.util.FocusModeManager
import com.example.vaultprepssc.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val focusModeManager: FocusModeManager,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val isOffline = focusModeManager.isOffline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
        
    private val _isPremium = MutableStateFlow(preferenceManager.isPremium())
    val isPremium = _isPremium.asStateFlow()

    fun purchasePremium() {
        preferenceManager.setPremium(true)
        _isPremium.value = true
    }
}
