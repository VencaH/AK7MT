package com.example.timetrack

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ItemHistoryBinding
import com.example.timetrack.domain.TimeRecordDomain

class HistoryListAdapter:
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>(){
        var timeData: MutableList<TimeRecordDomain> = mutableListOf()

    fun setTimeRecords(timeRecords: List<TimeRecordDomain>) {
        this.timeData = timeRecords.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryListAdapter.ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryListAdapter.ViewHolder, position: Int) {
        holder.date.text = timeData[position].date
        holder.time.text = timeData[position].timeRecord
        holder.textStatus.text = timeData[position].status
    }

    override fun getItemCount(): Int {
       return timeData.size
    }

        inner class ViewHolder(binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
            var date: TextView = binding.date
            var time: TextView = binding.time
            var textStatus: TextView = binding.textStatus
        }
}