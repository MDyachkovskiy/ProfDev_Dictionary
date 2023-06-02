package gb.com.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import gb.com.databinding.FragmentSuccessSearchResultBinding
import gb.com.model.data.WordDefinition
import gb.com.view.adapters.WordDefinitionAdapter

class SearchResult : Fragment() {

    companion object {
        private const val ARG_WORD_DEFINITIONS = "ARGUMENT_WORD_DEFINITIONS"

        fun newInstance(wordDefinition: List<WordDefinition>) : SearchResult {
            val fragment = SearchResult()
            val args = Bundle()
            args.putParcelableArrayList(ARG_WORD_DEFINITIONS, ArrayList(wordDefinition))
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentSuccessSearchResultBinding? = null
    private val binding get() = _binding!!

    private var adapter: WordDefinitionAdapter? = null

    private lateinit var wordDefinitons: List<WordDefinition>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordDefinitons = it.getParcelableArrayList(ARG_WORD_DEFINITIONS) ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentSuccessSearchResultBinding.inflate(inflater, container, false).also{
            _binding = it}
        adapter = WordDefinitionAdapter(wordDefinitons)
        with(binding){
            fragmentSuccessResultRecyclerview.layoutManager = LinearLayoutManager(context)
            fragmentSuccessResultRecyclerview.adapter = adapter

        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}