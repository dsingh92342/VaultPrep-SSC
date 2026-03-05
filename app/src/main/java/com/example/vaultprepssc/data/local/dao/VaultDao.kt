package com.example.vaultprepssc.data.local.dao

import androidx.room.*
import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.local.entity.TestSession
import com.example.vaultprepssc.data.local.entity.UserAttempt
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {
    // Questions
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int

    @Query("SELECT * FROM questions WHERE topicId = :topicId")
    fun getQuestionsByTopic(topicId: String): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: String): Question?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT :limit")
    fun getRandomQuestions(limit: Int): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE subjectId = :subjectId ORDER BY RANDOM() LIMIT :limit")
    fun getRandomQuestionsBySubject(subjectId: String, limit: Int): Flow<List<Question>>

    // Attempts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: UserAttempt)

    @Query("SELECT * FROM user_attempts WHERE sessionId = :sessionId")
    fun getAttemptsForSession(sessionId: String): Flow<List<UserAttempt>>

    @Query("SELECT * FROM user_attempts WHERE isCorrect = 0")
    fun getMistakes(): Flow<List<UserAttempt>>

    // Sessions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: TestSession)

    @Query("SELECT * FROM test_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<TestSession>>

    @Query("SELECT * FROM test_sessions WHERE id = :id")
    suspend fun getSessionById(id: String): TestSession?
}
