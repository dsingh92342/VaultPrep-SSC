package com.example.vaultprepssc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_sessions")
data class TestSession(
    @PrimaryKey val id: String,
    val testName: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val isFocusModeActive: Boolean = false,
    val sessionType: String // "PYQ", "DRILL", "MOCK"
)
