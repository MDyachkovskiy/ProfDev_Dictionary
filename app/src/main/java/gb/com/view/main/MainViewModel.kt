package gb.com.view.main

import androidx.lifecycle.LiveData
import gb.com.model.data.AppState
import gb.com.model.datasource.DataSourceRemote
import gb.com.model.repository.RepositoryImplementation
import gb.com.presenter.MainInteractor
import gb.com.viewmodel.BaseViewModel
import io.reactivex.observers.DisposableObserver


class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean){
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe{
                    liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
               // no op-code
            }
        }
    }
}