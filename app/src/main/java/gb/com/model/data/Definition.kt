package gb.com.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Definition(
    val definition: String = ""
) : Parcelable