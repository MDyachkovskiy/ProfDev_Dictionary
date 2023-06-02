package gb.com.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import gb.com.R
import gb.com.model.data.WordDefinition

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder> () {

    private var data: List<WordDefinition> = arrayListOf()

    fun setData(data: List<WordDefinition>){
        this.data = data
        notifyDataSetChanged()
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind (data: WordDefinition) {
            if (layoutPosition != RecyclerView.NO_POSITION){
                itemView.findViewById<TextView>(R.id.header_history_textview_recycler_item).text =
                    data.word
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context,
                        "on click: ${data.word}",
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_recycler_view, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

}