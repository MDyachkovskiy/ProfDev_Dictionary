package gb.com.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.databinding.FragmentSearchBinding
import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDTO
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.presenter.MainInteractor
import gb.com.utils.network.isOnline
import gb.com.view.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<AppState, MainInteractor, WordDefinition>() {

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
        adapter = WordDefinitionAdapter(data, parentFragmentManager, object: OnFavoriteChanged{
            override fun onFavoriteChanged(wordDTO: WordDTO) {
                model.saveFavorite(wordDTO)
            }
        })
        with(binding) {
            successResultRecyclerview.layoutManager = LinearLayoutManager(context)
            successResultRecyclerview.adapter = adapter
        }
    }

    private fun initViewModel() {
        val viewModel: SearchViewModel by viewModel()
        model = viewModel
        lifecycleScope.launch{
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.stateFlow.collect { renderData(it) }
            }
        }
    }
}