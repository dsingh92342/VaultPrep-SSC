package com.example.vaultprepssc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vaultprepssc.data.local.dao.VaultDao
import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.local.entity.TestSession
import com.example.vaultprepssc.data.local.entity.UserAttempt

@Database(
    entities = [Question::class, UserAttempt::class, TestSession::class],
    version = 1,
    exportSchema = false
)
abstract class VaultDatabase : RoomDatabase() {
    abstract fun vaultDao(): VaultDao
}
