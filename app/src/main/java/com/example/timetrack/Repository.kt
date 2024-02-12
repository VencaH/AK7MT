package com.example.timetrack

import android.util.Log
import com.example.timetrack.api.TaskDataNetwork
import com.example.timetrack.api.TrelloApi
import com.example.timetrack.api.mapToDatabase
import com.example.timetrack.database.ListDataDTO
import com.example.timetrack.database.MyDatabase
import com.example.timetrack.database.TaskDataDTO
import com.example.timetrack.database.mapToDomain
import com.example.timetrack.domain.ListDataDomain
import com.example.timetrack.domain.ListDataNetwork
import com.example.timetrack.domain.TaskDataDomain
import com.example.timetrack.domain.mapToDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository (private val api: TrelloApi, private val database: MyDatabase) {
    private val apiKey: String = Secrets.apikey
    private val token: String = Secrets.token
     suspend fun getTasksInfo(): List<TaskDataDomain>  {
      downloadTasks()
      return database.taskInfoDao.getTaskList().map { it.mapToDomain() }

     }

    suspend fun downloadTasks() {
        try{
            val response = api.getTaskInfo("cTQqySiV", apiKey, token)
            val taskDataNetwork = response.body()
            var listIds:MutableList<String> = mutableListOf()
            if (taskDataNetwork!=null) {
                val listTaskDataDTO = taskDataNetwork.map { it.mapToDatabase() }
                withContext(Dispatchers.IO) {
                    listTaskDataDTO.iterator().forEach { taskData ->
                        if (!listIds.contains(taskData.idList)) {
                            downloadListInfo(taskData.idList)
                            listIds.add(taskData.idList)
                        }
                        database.taskInfoDao.insertTaskData(taskData)
                }

                } }

            } catch (e:Exception) {
                Log.e("TimeTrack", e.message.orEmpty())
            }
    }
    suspend fun downloadListInfo(idList: String) {
        try {
            val list: ListDataNetwork? = api.getListInfo(idList, apiKey, token).body()
            if(list != null) {
                val listDataDTO = list.mapToDatabase()

                withContext(Dispatchers.IO) {
                    database.listDataDao.insertListData(listDataDTO)
                }
            }
        } catch (e:Exception) {
            Log.e("TimeTrack", e.message.orEmpty())
        }
    }


//   suspend fun getStatus(idList:String) : String {
//        var result = ""
//        val list = this.lists.find { list -> list.id.equals(idList) }
//        if (list == null) {
//            val newList = getListInfo(idList)
//            if (newList != null) {
//                this.lists.add(newList)
//                result = newList.name
//            }
//
//        } else { result = list.name}
//        return result
//    }

    suspend fun saveTime(idCard: String, time: String): Int {
        val response = api.postComment(idCard, time, apiKey, token)
        val code= response.code()
        return code
    }
}