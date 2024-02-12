package com.example.timetrack

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ActivityHistoryBinding

class HistoryActivity: AppCompatActivity() {
        private lateinit var historylist: RecyclerView
        private lateinit var adapter: HistoryListAdapter
        private lateinit var binding: ActivityHistoryBinding
        private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val app = application as ApiHandler
        viewModel = ViewModelProvider(this, HistoryViewModelFactory(app.repository)).get(HistoryViewModel::class.java)
        binding.historyViewModel = viewModel
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)
        val title = intent.extras?.getString("task_name")
        viewModel.title.value = title
        val id = intent.extras?.getString("id")
        viewModel.idCard.value = id

        historylist = binding.historyList
        historylist.layoutManager = LinearLayoutManager(this)

        adapter = HistoryListAdapter()

        historylist.adapter = adapter

        viewModel.timeRecords.observe(this, Observer {
            adapter.setTimeRecords(it)
        })
        viewModel.getCurrentTimeRecords()
    }
}