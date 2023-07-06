package gb.com.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gb.com.databinding.FragmentFavoriteBinding
import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDTO
import gb.com.presenter.FavoriteInteractor
import gb.com.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<AppState, FavoriteInteractor, WordDTO>() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override lateinit var model: FavoriteViewModel

    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        val viewModel: FavoriteViewModel by viewModel()
        model = viewModel
        model.getData("", false)
        model.subscribe().observe(viewLifecycleOwner){
            renderData(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun setupData(data: List<WordDTO>) {
        adapter.setData(data)
        initView()
    }

    private fun initView() {
        binding.favoriteRecyclerView.adapter = adapter
    }
}