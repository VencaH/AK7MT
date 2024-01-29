package com.example.timetrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryListAdapter(private val historyList: List<Pair<String,String>>):
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryListAdapter.ViewHolder, position: Int) {
        holder.date.text = historyList[position].first
        holder.time.text = historyList[position].second
    }

    override fun getItemCount(): Int {
       return historyList.size
    }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var date: TextView = itemView.findViewById(R.id.date)
            var time: TextView = itemView.findViewById(R.id.time)
        }
}