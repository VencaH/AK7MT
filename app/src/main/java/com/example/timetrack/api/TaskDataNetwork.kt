package com.example.timetrack.api

import com.example.timetrack.database.TaskDataDTO

data class TaskDataNetwork (
    val name: String,
    val idList: String,
    val id: String,
)

fun TaskDataNetwork.mapToDatabase(): TaskDataDTO{
    return TaskDataDTO(
        id =this.id,
        name = this.name,
        idList = this.idList,
    )
}