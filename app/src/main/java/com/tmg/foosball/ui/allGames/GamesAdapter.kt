package com.tmg.foosball.ui.allGames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmg.foosball.R
import com.tmg.foosball.model.GameResultModel


class GamesAdapter(
    private val dataSet: ArrayList<GameResultModel>,
    private val itemClickListener: OnItemClickListener,
    private val onLongClickListener: OnItemLongClickListener
) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewPerson1: TextView
        val textViewScore1: TextView
        val textViewPerson2: TextView
        val textViewScore2: TextView

        init {
            textViewPerson1 = view.findViewById(R.id.textViewPerson1)
            textViewScore1 = view.findViewById(R.id.textViewScore1)
            textViewPerson2 = view.findViewById(R.id.textViewPerson2)
            textViewScore2 = view.findViewById(R.id.textViewScore2)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.game_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textViewPerson1.text = dataSet[position].player1Name
        viewHolder.textViewScore1.text = dataSet[position].player1Score.toString()
        viewHolder.textViewPerson2.text = dataSet[position].player2Name
        viewHolder.textViewScore2.text = dataSet[position].player2Score.toString()

        viewHolder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(dataSet[position].id)
        }

        viewHolder.itemView.setOnLongClickListener {
            onLongClickListener.onItemLongClicked(position)
            true
        }
    }

    override fun getItemCount() = dataSet.size

}

interface OnItemClickListener {
    fun onItemClicked(itemId: Int)
}

interface OnItemLongClickListener {
    fun onItemLongClicked(position: Int)
}
