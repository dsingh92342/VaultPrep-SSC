package com.example.vaultprepssc

import android.app.Application
import com.example.vaultprepssc.data.local.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class VaultPrepApplication : Application() {
    @Inject lateinit var dbInitializer: DatabaseInitializer

    override fun onCreate() {
        super.onCreate()
        MainScope().launch {
            dbInitializer.populateIfEmpty()
        }
    }
}
