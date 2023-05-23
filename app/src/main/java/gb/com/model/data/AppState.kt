package gb.com.model.data

sealed class AppState {
    data class Success(val data: List<WordDefinition>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}