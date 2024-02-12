package com.example.timetrack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HistoryViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            HistoryViewModel(this.repository) as T
        } else  {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}