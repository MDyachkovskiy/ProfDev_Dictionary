package gb.com.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordDefinition(
    val word: String = "",
    val phonetic: String? = "",
    val meanings: List<Meaning>? = listOf()
) : Parcelable