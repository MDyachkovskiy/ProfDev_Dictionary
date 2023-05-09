package gb.com.application

import android.app.Application
import gb.com.di.AppComponent

class App : Application() {

    val appComponent: AppComponent =
        DaggerAppComponent
            .builder()
            .application(this)
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}