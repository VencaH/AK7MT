package com.example.timetrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var taskList: RecyclerView
    private lateinit var adapter: TaskListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        taskList = binding.taskList
        taskList.layoutManager = LinearLayoutManager(this)

        val taskCurrentList = listOf(Pair("Task 1","Doing"),Pair("Task 2","To Do"),Pair("Task 3","Doing"),Pair("Task 4","Done"))
        println(taskCurrentList)
        adapter = TaskListAdapter(taskCurrentList)

        taskList.adapter = adapter
        adapter.setOnItemClickListener(object : TaskListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, TrackActivity::class.java)
                val bundle = Bundle()
                val title = taskCurrentList[position].first
                bundle.putString("task_name", title)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })

    }
}