package com.example.timetrack.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Dao
interface TaskInfoDao {
    @Query("select * from task_data")
    fun getTaskList(): List<TaskDataStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskData(data: TaskDataDTO)
}

@Dao
interface ListDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListData(data: ListDataDTO)
}

@Dao
interface TimeRecordDao {
    @Query("select * from time_record where id = :id ")
    fun getTimeRecords(id: String): List<TimeRecordDTO>

    @Query("select * from time_record where status ='Not synced' and id = :id ")
    fun getAllNotSynced(id: String): List<TimeRecordDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimeRecord(data: TimeRecordDTO)

    @Update
    fun updateTimeRecord(data: TimeRecordDTO)
}

@Database(entities = [ListDataDTO::class, TaskDataDTO::class, TimeRecordDTO::class], version = 1)
    abstract class MyDatabase: RoomDatabase() {
        abstract val taskInfoDao: TaskInfoDao
       abstract val listDataDao:ListDataDao
       abstract val timeRecordDao: TimeRecordDao
    }

private lateinit var INSTANCE: MyDatabase

fun getDatabase(context: Context): MyDatabase {
    synchronized(MyDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "my_database").build()
        }
    }
    return INSTANCE
}