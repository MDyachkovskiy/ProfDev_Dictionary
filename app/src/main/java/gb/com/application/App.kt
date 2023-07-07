package gb.com.application

import android.app.Application
import gb.com.di.application
import gb.com.di.favoriteScreen
import gb.com.di.historyScreen
import gb.com.di.imageScreen
import gb.com.di.mainScreen
import gb.com.di.network
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules (listOf(application, mainScreen, historyScreen, imageScreen, favoriteScreen, network))
        }
    }
}