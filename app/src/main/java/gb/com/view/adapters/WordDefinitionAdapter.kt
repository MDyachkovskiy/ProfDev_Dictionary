package gb.com.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gb.com.R
import gb.com.model.data.Definition
import gb.com.model.data.Meaning
import gb.com.model.data.WordDefinition

class WordDefinitionAdapter(
    private val wordDefinitionList: List<WordDefinition>
) : RecyclerView.Adapter<WordDefinitionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val wordTextView: TextView = itemView.findViewById(
            R.id.header_textview_recycler_item
        )

        private val phoneticTextView: TextView = itemView.findViewById(
            R.id.phonetic_textview_recycler_item
        )

        private val partOfSpeechRecyclerView: RecyclerView = itemView.findViewById(
            R.id.partofspeech_recycler_view
        )

        fun bind(wordDefinition: WordDefinition) {
            wordTextView.text = wordDefinition.word
            phoneticTextView.text = wordDefinition.phonetic
            val partOfSpeechAdapter = PartOfSpeechAdapter(wordDefinition.meanings)
            partOfSpeechRecyclerView.apply{
                layoutManager = LinearLayoutManager(context)
                adapter = partOfSpeechAdapter
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordDefinition = wordDefinitionList[position]
        holder.bind(wordDefinition)
    }

    override fun getItemCount(): Int = wordDefinitionList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    inner class PartOfSpeechAdapter(
        private val meanings: List<Meaning>
    ) : RecyclerView.Adapter<PartOfSpeechAdapter.MeaningViewHolder>() {

        inner class MeaningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            private val partOfSpeechTextView: TextView =
                itemView.findViewById(R.id.partofspeech_textview_recycler_item)
            private val definitionRecyclerView: RecyclerView =
                itemView.findViewById(R.id.definition_recycler_view)

            fun bind(meaning: Meaning) {
                partOfSpeechTextView.text = meaning.partOfSpeech
                val definitionAdapter = DefinitionAdapter(meaning.definitions)
                definitionRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = definitionAdapter
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_part_of_speech, parent, false)
            return MeaningViewHolder(view)
        }

        override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
            holder.bind(meanings[position])
        }

        override fun getItemCount() = meanings.size
    }

    inner class DefinitionAdapter(
        private val definitions: List<Definition>
    ) : RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>() {

        inner class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val definitionTextView: TextView = itemView.findViewById(
                R.id.description_textview_recycler_item)

            fun bind(definition: Definition){
                definitionTextView.text = definition.definition
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_definition, parent, false)
            return DefinitionViewHolder(view)
        }

        override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
            holder.bind(definitions[position])
        }

        override fun getItemCount() = definitions.size
    }
}
