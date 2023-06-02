package gb.com.application

import android.app.Application
import gb.com.di.application
import gb.com.di.historyScreen
import gb.com.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules (listOf(application, mainScreen, historyScreen))
        }
    }
}