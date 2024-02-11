package com.example.timetrack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TrackActivityViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TrackActivityViewModel::class.java)) {
            TrackActivityViewModel(this.repository) as T
        } else  {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}