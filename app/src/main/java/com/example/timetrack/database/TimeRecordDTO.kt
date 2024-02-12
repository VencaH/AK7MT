package com.example.timetrack.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.timetrack.domain.TimeRecordDomain

@Entity(tableName = "time_record")
data class TimeRecordDTO (
    @PrimaryKey(autoGenerate = true)
    val rid: Int? =0,
    val id: String,
    val date: String,
    val timeRecord: String,
    var status: String
)

fun TimeRecordDTO.mapToDomain(): TimeRecordDomain {
    return TimeRecordDomain(
        rid =this.rid,
        id = this.id,
        timeRecord = this.timeRecord,
        status = this.status,
        date = this.date,
    )
}
