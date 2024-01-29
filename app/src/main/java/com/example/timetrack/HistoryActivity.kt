package com.example.timetrack

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity: AppCompatActivity() {
        private lateinit var historylist: RecyclerView
        private lateinit var adapter: HistoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val title = intent.getExtras()?.getString("task_name")
        val titleText: TextView = findViewById(R.id.taskHeaderHistory)
        titleText.text = title

        historylist = findViewById(R.id.historyList)
        historylist.layoutManager = LinearLayoutManager(this)

        val historyRecords = listOf(Pair("28.1.2024", "15m 42s"), Pair("19.1.2024", "1h 20m 3s"), Pair("29.1.2024", "6h 35m 20s"))
        adapter = HistoryListAdapter(historyRecords)

        historylist.adapter = adapter
    }
}