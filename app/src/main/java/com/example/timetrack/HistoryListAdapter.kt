package com.example.timetrack

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ItemHistoryBinding

class HistoryListAdapter(private val historyList: List<Pair<String,String>>):
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryListAdapter.ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryListAdapter.ViewHolder, position: Int) {
        holder.date.text = historyList[position].first
        holder.time.text = historyList[position].second
    }

    override fun getItemCount(): Int {
       return historyList.size
    }

        inner class ViewHolder(binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
            var date: TextView = binding.date
            var time: TextView = binding.time
        }
}