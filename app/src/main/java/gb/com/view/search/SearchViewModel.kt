package gb.com.view.search

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDTO
import gb.com.presenter.MainInteractor
import gb.com.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading(null))
    val stateFlow: StateFlow<AppState> get() = _stateFlow

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

    fun saveFavorite(wordDTO: WordDTO){
        viewModelCoroutineScope.launch {
            try{
                interactor.saveFavorite(wordDTO)
            } catch (e:Throwable){
                _stateFlow.emit(AppState.Error(e))
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