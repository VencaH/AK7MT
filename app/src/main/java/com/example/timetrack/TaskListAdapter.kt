package com.example.timetrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ItemTaskBinding

class TaskListAdapter(private val taskList: List<Pair<String,String>>):
 RecyclerView.Adapter<TaskListAdapter.ViewHolder>(){
 private lateinit var clickListener: onItemClickListener


 interface onItemClickListener {
  fun onItemClick(position: Int)
 }

 fun setOnItemClickListener(listener: onItemClickListener){
  clickListener = listener
 }

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.ViewHolder {
   val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent,false)
//    LayoutInflater.from(parent.context)
//    .inflate(R.layout.item_task, parent, false)
  return ViewHolder(binding, clickListener)
 }

 override fun onBindViewHolder(holder: TaskListAdapter.ViewHolder, position: Int) {
  holder.taskName.text = taskList[position].first
  holder.taskStatus.text = taskList[position].second
 }

 override fun getItemCount(): Int {
  return taskList.size
 }
   inner class ViewHolder(binding: ItemTaskBinding, listener: onItemClickListener):RecyclerView.ViewHolder(binding.root){
    private var taskButton: Button = binding.taskButton
    var taskName: TextView = binding.taskName
    var taskStatus: TextView = binding.taskStatus

    init {
      taskButton.setOnClickListener { listener.onItemClick(adapterPosition) }
    }

   }
 }