package gb.com.application

import android.app.Application

class App : Application() {

    val appComponent =
        DaggerAppComponent
            .builder()
            .appModule(this)
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}