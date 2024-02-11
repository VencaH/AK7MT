package com.example.timetrack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TaskListViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
          TaskListViewModel(this.repository) as T
      } else  {
          throw IllegalArgumentException("ViewModel Not Found")
      }
    }
}