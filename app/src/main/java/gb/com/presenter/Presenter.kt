package gb.com.presenter


import gb.com.model.data.AppState
import gb.com.view.base.View

interface Presenter <T: AppState, V: View> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}