package com.example.timetrack

import android.app.Application
import com.example.timetrack.api.TrelloApi
import com.example.timetrack.database.getDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHandler : Application() {
    private val apiService: TrelloApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.trello.com/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(TrelloApi::class.java)
    }

    val repository: Repository by lazy {
        Repository(apiService, getDatabase(this))
    }

}