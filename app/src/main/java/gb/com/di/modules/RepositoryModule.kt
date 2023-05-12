package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.di.NAME_LOCAL
import gb.com.di.NAME_REMOTE
import gb.com.model.data.WordDefinition
import gb.com.model.datasource.DataSource
import gb.com.model.datasource.api.RetrofitImplementation
import gb.com.model.datasource.db.RoomDataBaseImplementation
import gb.com.model.repository.Repository
import gb.com.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSourceRemote: DataSource<List<WordDefinition>>
    ) : Repository <List<WordDefinition>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun providesRepositoryLocal(
        @Named(NAME_LOCAL) dataSourceLocal: DataSource<List<WordDefinition>>
    ) : Repository <List<WordDefinition>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote() : DataSource<List<WordDefinition>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal() : DataSource<List<WordDefinition>> =
        RoomDataBaseImplementation()

}