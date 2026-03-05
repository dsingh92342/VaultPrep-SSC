package com.example.vaultprepssc.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun isPremium(): Boolean {
        return sharedPrefs.getBoolean(KEY_IS_PREMIUM, false)
    }

    fun setPremium(value: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_IS_PREMIUM, value).apply()
    }

    companion object {
        private const val KEY_IS_PREMIUM = "is_premium"
    }
}
