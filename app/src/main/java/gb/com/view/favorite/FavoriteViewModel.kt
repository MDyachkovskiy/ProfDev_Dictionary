package gb.com.view.favorite

import androidx.lifecycle.LiveData
import gb.com.model.data.wordDefinition.AppState
import gb.com.presenter.FavoriteInteractor
import gb.com.view.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val interactor: FavoriteInteractor
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean){
        _mutableLiveData.postValue(interactor.getData(word, isOnline))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}