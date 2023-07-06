package gb.com.model.repository

import androidx.lifecycle.LiveData
import gb.com.utils.network.OnlineLiveData

class OnlineRepository(
    private val onlineLiveData: OnlineLiveData
) {

    fun getNetworkStatusLiveData(): LiveData<Boolean> = onlineLiveData

}