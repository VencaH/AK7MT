package com.example.timetrack

import android.util.Log
import com.example.timetrack.api.TrelloApi
import com.example.timetrack.api.mapToDatabase
import com.example.timetrack.database.MyDatabase
import com.example.timetrack.database.mapToDomain
import com.example.timetrack.api.ListDataNetwork
import com.example.timetrack.domain.TaskDataDomain
import com.example.timetrack.domain.TimeRecordDomain
import com.example.timetrack.domain.mapToDTO
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
            val listIds:MutableList<String> = mutableListOf()
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
    private suspend fun downloadListInfo(idList: String) {
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
    fun getTimeRecords(idCard: String): List<TimeRecordDomain> {
        return database.timeRecordDao.getTimeRecords(idCard).map { it.mapToDomain() }
    }

   suspend fun saveTime(timeRecord: TimeRecordDomain):Int {
       var code = 0
       try {
           code = saveTimeNetwork(timeRecord.id, timeRecord.timeRecord)
           if (code == 200) {
               timeRecord.status = "Synced"
           }
       } catch (e:Exception) {
               Log.e("TimeTrack", e.message.orEmpty())
           }
       val timeRecordDTO = timeRecord.mapToDTO()
       withContext(Dispatchers.IO) {
           database.timeRecordDao.insertTimeRecord(timeRecordDTO)
       }
       return code
   }
    private suspend fun saveTimeNetwork(idCard: String, time: String): Int {
        val response = api.postComment(idCard, time, apiKey, token)
        val code= response.code()
        return code
    }

    suspend fun syncAll(id: String) {
        database.timeRecordDao.getAllNotSynced(id).iterator().forEach { timeRecord ->
            try {
                val code = saveTimeNetwork(timeRecord.id, timeRecord.timeRecord)
                if (code==200) {
                    timeRecord.status = "Synced"
                    database.timeRecordDao.updateTimeRecord(timeRecord)
                }
            } catch (e:Exception){
                Log.e("TimeTrack", e.message.orEmpty())
            }
        }
    }
}