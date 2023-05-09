package gb.com.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import gb.com.application.App
import gb.com.view.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(dictionaryApp: App)
    fun inject(mainActivity: MainActivity)
}