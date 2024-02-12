package com.example.timetrack.api

import com.example.timetrack.database.ListDataDTO

data class ListDataNetwork (
    val name: String,
    val status: String,
    val id: String,
)


fun ListDataNetwork.mapToDatabase(): ListDataDTO    {
    return ListDataDTO(
        name = this.name,
        id = this.id
    )
}