package com.example.timetrack.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.timetrack.domain.TaskDataDomain

@Entity(tableName = "task_data")
data class TaskDataDTO (
    @PrimaryKey
    val id: String,
    val name: String,
    val idList: String,
)

data class TaskDataStatus (
    @Embedded val TaskData : TaskDataDTO,
    @Relation(
        parentColumn= "idList",
        entityColumn= "id"
    )
    val list: ListDataDTO
)

fun TaskDataStatus.mapToDomain():TaskDataDomain{
    return TaskDataDomain(
        id = this.TaskData.id,
        name = this.TaskData.name,
        status = this.list.name,
    )
}