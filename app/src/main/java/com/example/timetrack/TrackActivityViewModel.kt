package com.example.timetrack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetrack.domain.TimeRecordDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate

class TrackActivityViewModel (private val repository: Repository): ViewModel(){
    val response = MutableLiveData<String>()
    var idCard = MutableLiveData<String>()
    var time = MutableLiveData<String>()
    var startstop = MutableLiveData<String>()
    var stopwatch = MutableLiveData<String?>()
    private var stopwatchState: Boolean = false

    init {
        time.value = "00:00:00"
        startstop.value = "Start"
        stopwatch.value = null
    }
    fun saveTime() {
        viewModelScope.launch(Dispatchers.IO) {
            if (idCard.value!= null && time.value!=null) {
                try {
                    val resp = repository.saveTime(
                        TimeRecordDomain(
                            rid = null,
                        id = idCard.value.orEmpty(),
                            timeRecord = time.value.orEmpty(),
                            status = "Not synced",
                            date = LocalDate.now().toString()))
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

    fun flipStopwatch() {
        if (!stopwatchState) {
            stopwatch.postValue("start")
            startstop.postValue("Stop")
            stopwatchState = true
        } else {
            stopwatch.postValue("stop")
            startstop.postValue( "Start")
            stopwatchState = false
        }
    }

    fun resetTimer() {
        time.value = "00:00:00"
    }

}