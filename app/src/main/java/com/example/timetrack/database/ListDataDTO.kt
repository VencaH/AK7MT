package com.example.timetrack.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_data")
data class ListDataDTO (
    @PrimaryKey
    val id: String,
    val name: String,
)