package com.example.vaultprepssc.util

import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.local.dao.VaultDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockGenerator @Inject constructor(
    private val vaultDao: VaultDao
) {
    /**
     * Generates a balanced 100-question mock test based on standard SSC CGL weightage:
     * - Quantitative Aptitude: 25 questions
     * - English Language: 25 questions
     * - General Intelligence & Reasoning: 25 questions
     * - General Awareness: 25 questions
     */
    suspend fun generateFullMock(): List<Question> {
        val quant = fetchRandomBySubject("quant", 25)
        val english = fetchRandomBySubject("english", 25)
        val reasoning = fetchRandomBySubject("reasoning", 25)
        val gs = fetchRandomBySubject("gs", 25)
        
        return (quant + english + reasoning + gs).shuffled()
    }

    private suspend fun fetchRandomBySubject(subjectId: String, limit: Int): List<Question> {
        return vaultDao.getRandomQuestionsBySubject(subjectId, limit).first()
    }
}
