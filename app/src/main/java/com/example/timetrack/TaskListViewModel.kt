package com.example.timetrack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetrack.api.TrelloApi
import com.example.timetrack.model.TaskData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskListViewModel (private val repository: Repository): ViewModel(){
    val taskList = MutableLiveData<List<TaskData>>()
    val errorMessage =MutableLiveData<String>()

    fun getCurrentTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTasksInfo()
            taskList.postValue(response)
        }
    }

}