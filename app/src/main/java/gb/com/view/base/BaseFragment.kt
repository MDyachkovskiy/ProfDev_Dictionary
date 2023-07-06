package gb.com.view.base

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import gb.com.R
import gb.com.databinding.LayoutLoadingBinding
import gb.com.model.data.wordDefinition.AppState
import gb.com.presenter.Interactor
import gb.com.utils.network.OnlineLiveData
import gb.com.view.alertDialog.AlertDialogFragment
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment<T: AppState, I: Interactor<T>,D> : ScopeFragment() {

    private var _binding: LayoutLoadingBinding? = null
    private val binding get() = _binding!!

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = true

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }

    private fun subscribeToNetworkChange() {

        var snackbar: Snackbar? = null

        OnlineLiveData(requireContext()).observe(viewLifecycleOwner
        ) {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                snackbar = Snackbar.make(
                    requireView(),
                    R.string.dialog_message_device_is_offline,
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar?.show()
            } else {
                snackbar?.dismiss()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LayoutLoadingBinding.inflate(layoutInflater)
        subscribeToNetworkChange()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isDialogNull(): Boolean{
        return parentFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    protected open fun renderData(appState: T) {
        when(appState) {
            is AppState.Success<*> -> {
                showViewWorking()
                val data = appState.data as? List<D>
                data?.let {
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

    private fun showViewLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    abstract fun setupData(data: List<D>)

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(parentFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun showViewWorking() {
        binding.loadingLayout.visibility = View.GONE
    }
}