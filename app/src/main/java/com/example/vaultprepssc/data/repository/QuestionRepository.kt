package com.example.vaultprepssc.data.repository

import com.example.vaultprepssc.data.local.dao.VaultDao
import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.local.entity.TestSession
import com.example.vaultprepssc.data.local.entity.UserAttempt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    private val vaultDao: VaultDao
) {
    fun getAllQuestions(): Flow<List<Question>> = vaultDao.getAllQuestions()

    fun getQuestionsByTopic(topicId: String): Flow<List<Question>> = 
        vaultDao.getQuestionsByTopic(topicId)

    suspend fun getQuestionById(id: String): Question? = 
        vaultDao.getQuestionById(id)

    fun getRandomQuestions(limit: Int): Flow<List<Question>> = 
        vaultDao.getRandomQuestions(limit)

    suspend fun saveAttempt(attempt: UserAttempt) = 
        vaultDao.insertAttempt(attempt)

    fun getMistakes(): Flow<List<UserAttempt>> = 
        vaultDao.getMistakes()

    suspend fun saveSession(session: TestSession) = 
        vaultDao.insertSession(session)

    fun getAllSessions(): Flow<List<TestSession>> = 
        vaultDao.getAllSessions()

    fun getAttemptsForSession(sessionId: String): Flow<List<UserAttempt>> = 
        vaultDao.getAttemptsForSession(sessionId)
}
