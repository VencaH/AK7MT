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
import java.lang.Exception

class TrackActivityViewModel (private val repository: Repository): ViewModel(){
    val response = MutableLiveData<String>()
    var idCard = MutableLiveData<String>()
    var time = MutableLiveData<String>()

    init {
        time.value = "00:00:00"
    }
    fun saveTime() {
        viewModelScope.launch(Dispatchers.IO) {
            if (idCard.value!= null && time.value!=null) {
                try {
                    val resp = repository.saveTime(idCard.value.orEmpty(), time.value.orEmpty())
                    if (resp == 200) {
                        response.postValue("Time saved!")
                    } else {
                        response.postValue("Error saving time, please try later.")
                    }
                } catch (e: Exception) {
                    response.postValue("Error saving time: Unable to reach server. Please try againg later.")
                }
            }
            }
    }

}