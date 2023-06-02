package gb.com.view.history

import android.os.Bundle
import gb.com.databinding.FragmentHistoryBinding
import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.presenter.HistoryInteractor
import gb.com.view.adapters.HistoryAdapter
import gb.com.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override lateinit var model: HistoryViewModel

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun setupData(data: List<WordDefinition>) {
        adapter.setData(data)
    }
    private fun initViewModel() {
       val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.getData("", false)
        model.subscribe().observe(this@HistoryActivity) {
            renderData(it)
        }
    }

    private fun initView() {
        binding.historyRecyclerView.adapter = adapter
    }
}