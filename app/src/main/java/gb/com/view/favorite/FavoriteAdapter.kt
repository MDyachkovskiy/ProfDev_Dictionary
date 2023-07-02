package gb.com.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gb.com.databinding.ItemFavoriteRecyclerViewBinding
import gb.com.model.data.wordDefinition.WordDTO

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteItemViewHolder> () {

    private var data: List<WordDTO> = arrayListOf()

    fun setData(data: List<WordDTO>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class FavoriteItemViewHolder(
        private val binding: ItemFavoriteRecyclerViewBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: WordDTO) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(binding) {
                    headerFavoriteRecyclerView.text = data.word
                    phoneticFavoriteRecyclerView.text = data.phonetic
                    partOfSpeechFavoriteRecyclerView.text = data.partOfSpeech
                    descriptionFavoriteRecyclerView.text = data.definition.definition
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteRecyclerViewBinding.inflate(inflater, parent, false)
        return FavoriteItemViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

}