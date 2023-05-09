package gb.com.di

import dagger.Module
import dagger.Provides
import gb.com.model.data.WordDefinition
import gb.com.model.repository.Repository
import gb.com.presenter.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<WordDefinition>>
    ) = MainInteractor(repositoryRemote)
}