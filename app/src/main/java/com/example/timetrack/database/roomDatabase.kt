package com.example.timetrack.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

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

@Database(entities = [ListDataDTO::class, TaskDataDTO::class], version = 1)
    abstract class MyDatabase: RoomDatabase() {
        abstract val taskInfoDao: TaskInfoDao
       abstract val listDataDao:ListDataDao
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
class roomDatabase {
}