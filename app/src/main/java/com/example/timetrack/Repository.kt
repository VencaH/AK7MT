package com.example.timetrack

import com.example.timetrack.api.TrelloApi
import com.example.timetrack.model.ListData
import com.example.timetrack.model.TaskApiData
import com.example.timetrack.model.TaskData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class Repository (private val api: TrelloApi) {
    private val apiKey: String = Secrets.apikey
    private val token: String = Secrets.token
    private var lists: MutableList<ListData> = mutableListOf()
     suspend fun getTasksInfo(): List<TaskData>  {
         val response = api.getTaskInfo("cTQqySiV", apiKey,token)

         return response.body().orEmpty().map { task -> TaskData(task.name, getStatus(task.idList), task.id) }

     }

    suspend fun getListInfo(idList: String): ListData? =
        api.getListInfo(idList, apiKey, token).body()


   suspend fun getStatus(idList:String) : String {
        var result = ""
        val list = this.lists.find { list -> list.id.equals(idList) }
        if (list == null) {
            val newList = getListInfo(idList)
            if (newList != null) {
                this.lists.add(newList)
                result = newList.name
            }

        } else { result = list.name}
        return result
    }

    suspend fun saveTime(idCard: String, time: String): Int {
        val response = api.postComment(idCard, time, apiKey, token)
        val code= response.code()
        return code
    }
}