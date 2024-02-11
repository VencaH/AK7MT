package com.example.timetrack

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ActivityHistoryBinding

class HistoryActivity: AppCompatActivity() {
        private lateinit var historylist: RecyclerView
        private lateinit var adapter: HistoryListAdapter
        private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val title = intent.getExtras()?.getString("task_name")
        val titleText: TextView = binding.taskHeaderHistory
        titleText.text = title

        historylist = binding.historyList
        historylist.layoutManager = LinearLayoutManager(this)

        val historyRecords = listOf(Pair("28.1.2024", "15m 42s"), Pair("19.1.2024", "1h 20m 3s"), Pair("29.1.2024", "6h 35m 20s"))
        adapter = HistoryListAdapter(historyRecords)

        historylist.adapter = adapter
    }
}