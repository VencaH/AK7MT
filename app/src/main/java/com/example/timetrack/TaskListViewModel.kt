package com.example.timetrack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetrack.domain.TaskDataDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel (private val repository: Repository): ViewModel(){
    val taskList = MutableLiveData<List<TaskDataDomain>>()

    fun getCurrentTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTasksInfo()
            taskList.postValue(response)
        }
    }

}