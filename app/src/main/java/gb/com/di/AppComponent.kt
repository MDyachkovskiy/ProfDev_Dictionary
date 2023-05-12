package gb.com.di

import dagger.Component
import gb.com.application.App
import gb.com.di.modules.*
import gb.com.di.modules.ViewModelModule
import gb.com.view.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class]
)

interface AppComponent {

    fun inject(dictionaryApp: App)
    fun inject(mainActivity: MainActivity)
}