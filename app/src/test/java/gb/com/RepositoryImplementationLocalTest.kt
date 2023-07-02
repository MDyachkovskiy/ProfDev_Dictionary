package gb.com

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.model.datasource.db.DataSourceLocal
import gb.com.model.repository.RepositoryImplementationLocal
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class RepositoryImplementationLocalTest {

    private val dataSource = FakeDataSource()
    private val repository = RepositoryImplementationLocal(dataSource)

    class FakeDataSource : DataSourceLocal<List<WordDefinition>> {

        private val cache: MutableMap<String, List<WordDefinition>> = mutableMapOf()

        var saveCalled = false
        var lastWord: String? = null

        override suspend fun saveToDB(appState: AppState) {
            saveCalled = true
        }

        override suspend fun getData(word: String): List<WordDefinition> {
            lastWord = word

            if (cache.containsKey(word)){
                return cache[word]!!
            } else {
                val data = generateData(word)
                cache[word] = data
                return data
            }
        }

        private fun generateData(word: String): List<WordDefinition> {
            return when(word) {
                "apple" -> listOf(
                    WordDefinition(word, "a fruit"),
                    WordDefinition(word, "a tech company"))
                "orange" -> listOf(WordDefinition(word, "a citrus"))
                else -> emptyList()
            }
        }
    }

    @Test
    fun `when saveToDB is called, it calls saveToDB on dataSource`() = runBlocking {
        val appState = AppState.Loading(null)
        repository.saveToDB(appState)
        assertTrue(dataSource.saveCalled)
    }

    @Test
    fun `when getData is called, it calls getData on data source`() = runBlocking {
        val word = "test"
        repository.getData(word)
        assertEquals(word, dataSource.lastWord)
    }

    @Test
    fun `when getData with different words returns different results`() = runBlocking {
        val wordOne = "apple"
        val wordTwo = "orange"
        val dataOne = repository.getData(wordOne)
        val dataTwo = repository.getData(wordTwo)
        assertNotEquals(dataOne, dataTwo)
    }

    @Test
    fun `getData returns correct data`() = runBlocking {
        val word = "apple"
        val expectedData = listOf(
            WordDefinition(word, "a fruit"),
            WordDefinition(word, "a tech company")
        ).toTypedArray()

        val actualData = repository.getData(word).toTypedArray()

        assertArrayEquals(expectedData, actualData)
    }

    @Test
    fun `getData returns not null for existing word`() = runBlocking {
        val word = "apple"
        val data = repository.getData(word)
        assertNotNull(data)
    }

    @Test
    fun `getData returns same data for the same word`() = runBlocking {
        val word = "apple"
        val dataOne = repository.getData(word)
        val dataTwo = repository.getData(word)
        assertSame(dataOne, dataTwo)
    }
}