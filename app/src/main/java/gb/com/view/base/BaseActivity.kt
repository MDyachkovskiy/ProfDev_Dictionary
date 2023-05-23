package gb.com.view.base


import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import gb.com.R
import gb.com.model.data.AppState
import gb.com.utils.network.isOnline
import gb.com.utils.ui.AlertDialogFragment
import gb.com.viewmodel.BaseViewModel

abstract class BaseActivity<T: AppState> : AppCompatActivity(){

    abstract fun renderData(appState: AppState)

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull())
            showNoInternetConnectionDialog()
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT) == null
    }

    protected fun showNoInternetConnectionDialog() {
        AlertDialogFragment.newInstance(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        ).show(supportFragmentManager, DIALOG_FRAGMENT)
    }


    companion object {
        private const val DIALOG_FRAGMENT = "dialog fragment"
    }

}