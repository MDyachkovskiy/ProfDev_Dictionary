package gb.com.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gb.com.databinding.FragmentHistoryBinding
import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.presenter.HistoryInteractor
import gb.com.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : BaseFragment<AppState, HistoryInteractor, WordDefinition>() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override lateinit var model: HistoryViewModel

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initViewModel()
        initView()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun setupData(data: List<WordDefinition>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
       val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.getData("", false)
        model.subscribe().observe(this@HistoryFragment) {
            renderData(it)
        }
    }

    private fun initView() {
        binding.historyRecyclerView.adapter = adapter
    }
}