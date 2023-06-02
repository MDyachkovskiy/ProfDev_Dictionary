package gb.com.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import gb.com.R
import gb.com.databinding.ActivityMainBinding
import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.presenter.MainInteractor
import gb.com.utils.network.isOnline
import gb.com.view.base.BaseActivity
import gb.com.view.history.HistoryActivity
import gb.com.view.search.SearchResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    override lateinit var model: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private var searchingWord: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupSearchView()
    }

    private fun setupSearchView() {

        isNetworkAvailable = isOnline(applicationContext)

        if(isNetworkAvailable) {
            with(binding) {
                searchView.setOnQueryTextListener(
                    object: SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            searchingWord = query
                            query?.let{
                                model.getData(it, true)
                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            newText?.let {
                                model.getPreliminaryData(it, true)
                            }
                            return true
                        }
                    }
                )
            }
        } else {
            showNoInternetConnectionDialog()
        }
    }

    /*override fun showErrorScreen(error: String?) {
        showViewError()
        binding.apply {
            errorTextView.text = error ?: getString(R.string.undefined_error)
            reloadButton.setOnClickListener{
                searchingWord?.let {
                    model.getData(it, true)
                }
            }
        }
    }*/

    override fun setupData(data: List<WordDefinition>) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.success_layout,
            SearchResult.newInstance(data))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initViewModel() {
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        lifecycleScope.launchWhenStarted{
            model.stateFlow.collect { renderData(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}