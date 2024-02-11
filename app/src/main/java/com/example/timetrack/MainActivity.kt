package com.example.timetrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var taskList: RecyclerView
    private lateinit var adapter: TaskListAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val app = application as ApiHandler
        viewModel = ViewModelProvider(this, TaskListViewModelFactory(app.repository)).get(TaskListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)

        taskList = binding.taskList
        taskList.layoutManager = LinearLayoutManager(this)

        adapter = TaskListAdapter()

        taskList.adapter = adapter
        adapter.setOnItemClickListener(object : TaskListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, TrackActivity::class.java)
                val bundle = Bundle()
                val title = adapter.tasks[position].name
                val id = adapter.tasks[position].id
                bundle.putString("task_name", title)
                bundle.putString("id", id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        viewModel.taskList.observe(this, Observer {
            Log.d("MainActivity", "onCreate: $it")
            adapter.setTaskList(it)
        })
        viewModel.errorMessage.observe(this, Observer {  })
        viewModel.getCurrentTaskList()

    }
}