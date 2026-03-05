package com.example.vaultprepssc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: String,
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctIdx: Int,
    val explanation: String,
    val topicId: String,
    val subjectId: String,
    val year: Int
)
