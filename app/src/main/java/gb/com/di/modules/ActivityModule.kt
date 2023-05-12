package gb.com.di.modules

import dagger.Module
import dagger.Provides
import gb.com.view.main.MainActivity

@Module
class ActivityModule {

    @Provides
    fun contributeMainActivity(): MainActivity = MainActivity()
}