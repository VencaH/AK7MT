package com.example.timetrack

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetrack.databinding.ItemTaskBinding
import com.example.timetrack.domain.TaskDataDomain

class TaskListAdapter:
 RecyclerView.Adapter<TaskListAdapter.ViewHolder>(){
  var tasks:MutableList<TaskDataDomain> = mutableListOf<TaskDataDomain>()
 fun setTaskList(tasks: List<TaskDataDomain>) {
  this.tasks = tasks.toMutableList()
  notifyDataSetChanged()
 }
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
  holder.taskName.text = tasks[position].name
  holder.taskStatus.text = tasks[position].status
 }

 override fun getItemCount(): Int {
  return tasks.size
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