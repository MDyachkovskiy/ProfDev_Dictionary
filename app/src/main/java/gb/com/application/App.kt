package gb.com.application

import android.app.Application
import gb.com.di.application
import gb.com.di.mainScreen
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules (listOf(application, mainScreen))
        }
    }
}