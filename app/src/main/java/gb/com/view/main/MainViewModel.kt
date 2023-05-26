package gb.com.view.main

import androidx.lifecycle.LiveData
import gb.com.model.data.AppState
import gb.com.presenter.MainInteractor
import gb.com.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    private val queryStateFlow = MutableStateFlow("")

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean){
       _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch{ startInteractor(word, isOnline) }
    }

    override fun getPreliminaryData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        viewModelCoroutineScope.launch {
            queryStateFlow.value = word
            queryStateFlow.debounce(500)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        emit(interactor.getData(query, isOnline))
                    }
                }.catch {error->
                    _mutableLiveData.postValue(AppState.Error(error))
                }
                .collect { state ->
                    _mutableLiveData.postValue(state)
                }
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
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