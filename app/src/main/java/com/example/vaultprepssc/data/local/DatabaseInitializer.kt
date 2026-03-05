import android.content.Context
import com.example.vaultprepssc.data.local.entity.Question
import com.example.vaultprepssc.data.local.dao.VaultDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val vaultDao: VaultDao,
    @ApplicationContext private val context: Context
) {
    suspend fun populateIfEmpty() {
        if (vaultDao.getQuestionCount() > 0) return

        try {
            val jsonString = context.assets.open("questions.json").bufferedReader().use { it.readText() }
            val questions = Json.decodeFromString<List<Question>>(jsonString)
            vaultDao.insertQuestions(questions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
