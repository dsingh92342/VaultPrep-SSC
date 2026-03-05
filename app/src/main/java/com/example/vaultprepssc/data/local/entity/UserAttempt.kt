package com.example.vaultprepssc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_attempts")
data class UserAttempt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: String,
    val sessionId: String,
    val selectedIdx: Int,
    val isCorrect: Boolean,
    val timeSpent: Long, // in seconds
    val timestamp: Long = System.currentTimeMillis()
)
