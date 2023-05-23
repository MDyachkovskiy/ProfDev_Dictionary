package gb.com.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gb.com.R
import gb.com.model.data.Definition
import gb.com.model.data.WordDefinition

class WordDefinitionAdapter(
    private val wordDefinitionList: List<WordDefinition>
) : RecyclerView.Adapter<WordDefinitionAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val wordTextView: TextView = itemView.findViewById(
            R.id.header_textview_recycler_item
        )
        private val definitionRecyclerView: RecyclerView = itemView.findViewById(
            R.id.definition_recycler_view
        )

        fun bindView(word: String, definitionList: List<Definition>) {
            wordTextView.text = word
            val defitnitionAdapter = DefinitionAdapter(definitionList)
            definitionRecyclerView.adapter = defitnitionAdapter
            definitionRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_search_result, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordDefinition = wordDefinitionList[position]

        val mergedDefinitionList = mutableListOf<Definition>()
        wordDefinition.meanings.forEach { meaning ->
            mergedDefinitionList.addAll(meaning.definitions)
        }

        holder.bindView(wordDefinition.word, mergedDefinitionList)
    }

    override fun getItemCount(): Int = wordDefinitionList.size

    class DefinitionAdapter(
        private val definitionList: List<Definition>
    ) : RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val definitionTextView: TextView = itemView.findViewById(
                R.id.description_textview_recycler_item
            )
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_definition, parent, false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val definition = definitionList[position]
            holder.definitionTextView.text = definition.definition
        }

        override fun getItemCount(): Int = definitionList.size
    }
}
