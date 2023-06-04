package gb.com.view.search

import gb.com.model.data.wordDefinition.AppState
import gb.com.presenter.MainInteractor
import gb.com.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading(null))
    val stateFlow: StateFlow<AppState> get() = _stateFlow

    private val queryStateFlow = MutableStateFlow("")

    override fun getData(word: String, isOnline: Boolean) {
        viewModelCoroutineScope.launch {
            _stateFlow.value = AppState.Loading(null)
            try{
                _stateFlow.emit(interactor.getData(word, isOnline))
            } catch (e: Throwable) {
                _stateFlow.emit(AppState.Error(e))
            }
        }
    }

    fun getPreliminaryData(word: String, isOnline: Boolean) {
        _stateFlow.value = AppState.Loading(null)
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
                    _stateFlow.emit(AppState.Error(error))
                }
                .collect { state ->
                    _stateFlow.emit(state)
                }
        }
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}