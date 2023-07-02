package gb.com.model.repository

import gb.com.model.data.wordImage.SkyengWord

interface RepositorySkyeng {
    suspend fun getSkyengWord(word: String): List<SkyengWord>
}