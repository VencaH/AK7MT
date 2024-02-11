package com.example.timetrack

import android.app.Application
import com.example.timetrack.api.TrelloApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHandler : Application() {
    val apiService: TrelloApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.trello.com/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(TrelloApi::class.java)
    }

    val repository: Repository by lazy {
        Repository(apiService)
    }

}