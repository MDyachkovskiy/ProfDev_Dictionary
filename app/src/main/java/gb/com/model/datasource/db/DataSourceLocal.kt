package gb.com.model.datasource.db

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.datasource.api.DataSource

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}