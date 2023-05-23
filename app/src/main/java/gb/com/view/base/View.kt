package gb.com.view.base

import gb.com.model.data.AppState

interface View {

    fun renderData(appState: AppState)

}