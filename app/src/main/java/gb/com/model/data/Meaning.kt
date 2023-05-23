package gb.com.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meaning(
    val partOfSpeech: String = "",
    val definitions: List<Definition> = listOf()
) : Parcelable