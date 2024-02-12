package com.example.timetrack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetrack.domain.TimeRecordDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository):ViewModel() {
    val timeRecords =MutableLiveData<List<TimeRecordDomain>>()
    val title = MutableLiveData<String>()
    val idCard = MutableLiveData<String>()

    fun getCurrentTimeRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTimeRecords(idCard.value.orEmpty())
            timeRecords.postValue(response)
        }
    }

    fun syncAll () {
        viewModelScope.launch(Dispatchers.IO) {
            repository.syncAll(idCard.value.orEmpty())
            val response = repository.getTimeRecords(idCard.value.orEmpty())
            timeRecords.postValue(response)
        }
    }
}