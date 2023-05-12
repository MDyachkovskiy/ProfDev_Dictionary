package gb.com.view.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gb.com.R
import gb.com.application.App
import gb.com.databinding.ActivityMainBinding
import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.utils.network.isOnline
import gb.com.view.base.BaseActivity
import gb.com.view.fragments.SearchResult
import javax.inject.Inject

class MainActivity : BaseActivity<AppState>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override lateinit var model: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private val observer = Observer<AppState> {renderData(it)}

    private var searchingWord: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (application as App).appComponent
        appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity, observer)

        with(binding) {
            searchView.setOnQueryTextListener(
                object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchingWord = query
                        isNetworkAvailable = isOnline(applicationContext)
                        if(isNetworkAvailable) {
                            query?.let{
                                model.getData(it, true)
                                model.subscribe().observe(this@MainActivity, observer)
                            }
                        } else {
                            showNoInternetConnectionDialog()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                }
            )
        }
    }

    override fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                val data = appState.data
                if (data == null || data.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    showSearchResultsFragment(data)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if(appState.progress != null)
                    binding.apply {
                        progressBarHorizontal.visibility = VISIBLE
                        progressBarRound.visibility = GONE
                        progressBarHorizontal.progress = appState.progress
                    } else {
                        binding.apply {
                            progressBarHorizontal.visibility = GONE
                            progressBarRound.visibility = VISIBLE
                        }
                    }
                }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.apply {
            errorTextView.text = error ?: getString(R.string.undefined_error)
            reloadButton.setOnClickListener{
                searchingWord?.let {
                    model.getData(it, true)
                    model.subscribe().observe(this@MainActivity, observer)
                }
            }
        }
    }

    private fun showViewSuccess() {
        binding.apply {
            loadingLayout.visibility = GONE
            errorLayout.visibility = GONE
            successLayout.visibility = VISIBLE
        }
    }

    private fun showViewLoading() {
        binding.apply {
            loadingLayout.visibility = VISIBLE
            errorLayout.visibility = GONE
            successLayout.visibility = GONE
        }
    }

    private fun showViewError() {
        binding.apply {
            loadingLayout.visibility = GONE
            errorLayout.visibility = VISIBLE
            successLayout.visibility = GONE
        }
    }

    private fun showSearchResultsFragment(data: List<WordDefinition>) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.success_layout,
            SearchResult.newInstance(data))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}