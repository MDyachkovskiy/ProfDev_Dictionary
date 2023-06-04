package gb.com.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.databinding.FragmentSearchBinding
import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.presenter.MainInteractor
import gb.com.utils.network.isOnline
import gb.com.view.adapters.WordDefinitionAdapter
import gb.com.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<AppState, MainInteractor>() {

    override lateinit var model: SearchViewModel

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchingWord: String? = null

    private var adapter: WordDefinitionAdapter? = null

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        initViewModel()
        setupSearchView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchView() {

        isNetworkAvailable = isOnline(requireContext())

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

    override fun setupData(data: List<WordDefinition>) {
        adapter = WordDefinitionAdapter(data)
        with(binding) {
            successResultRecyclerview.layoutManager = LinearLayoutManager(context)
            successResultRecyclerview.adapter = adapter
        }
    }

    private fun initViewModel() {
        val viewModel: SearchViewModel by viewModel()
        model = viewModel
        lifecycleScope.launchWhenStarted{
            model.stateFlow.collect { renderData(it) }
        }
    }
}