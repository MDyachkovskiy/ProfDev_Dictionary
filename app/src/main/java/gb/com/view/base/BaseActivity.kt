package gb.com.view.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import gb.com.R
import gb.com.databinding.LayoutLoadingBinding
import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.presenter.Interactor
import gb.com.utils.network.isOnline
import gb.com.view.alertDialog.AlertDialogFragment

abstract class BaseActivity<T: AppState, I: Interactor<T>> : AppCompatActivity(){

    private lateinit var binding: LayoutLoadingBinding

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutLoadingBinding.inflate(layoutInflater)

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

    protected open fun renderData(appState: T){
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let{
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.title_no_definitions),
                            getString(R.string.error_no_definitions)
                        )
                    } else {
                        setupData(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                with(binding) {
                    if (appState.progress != null) {
                            progressBarHorizontal.visibility = View.VISIBLE
                            progressBarRound.visibility = View.GONE
                            progressBarHorizontal.progress = appState.progress
                        } else {
                            progressBarHorizontal.visibility = View.GONE
                            progressBarRound.visibility = View.VISIBLE
                        }
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.dialog_title_stub), appState.error.message)
            }
        }
    }

    protected fun showAlertDialog (title: String?, message: String?){
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun showViewWorking() {
        binding.loadingLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    protected fun showNoInternetConnectionDialog() {
        AlertDialogFragment.newInstance(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        ).show(supportFragmentManager, DIALOG_FRAGMENT)
    }

    abstract fun setupData(data: List<WordDefinition>)

    companion object {
        private const val DIALOG_FRAGMENT = "dialog fragment"
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }
}