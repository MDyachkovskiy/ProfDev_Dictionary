package gb.com.view.base


import androidx.appcompat.app.AppCompatActivity
import gb.com.model.data.AppState
import gb.com.viewmodel.BaseViewModel

abstract class BaseActivity<T: AppState> : AppCompatActivity(){

    abstract fun renderData(appState: AppState)

    abstract val model: BaseViewModel<T>

}