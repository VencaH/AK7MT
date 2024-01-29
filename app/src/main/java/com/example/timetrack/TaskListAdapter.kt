package com.example.timetrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
   val view = LayoutInflater.from(parent.context)
    .inflate(R.layout.item_task, parent, false)
  return ViewHolder(view, clickListener)
 }

 override fun onBindViewHolder(holder: TaskListAdapter.ViewHolder, position: Int) {
  holder.taskName.text = taskList[position].first
  holder.taskStatus.text = taskList[position].second
 }

 override fun getItemCount(): Int {
  return taskList.size
 }
   inner class ViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
    var taskButton: Button = itemView.findViewById(R.id.taskButton)
    var taskName: TextView = itemView.findViewById(R.id.taskName)
    var taskStatus: TextView = itemView.findViewById(R.id.taskStatus)

    init {
      taskButton.setOnClickListener { listener.onItemClick(adapterPosition) }
    }

   }
 }