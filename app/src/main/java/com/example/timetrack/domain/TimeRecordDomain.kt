package com.example.timetrack.domain

import com.example.timetrack.database.TimeRecordDTO

data class TimeRecordDomain (
    val rid: Int?,
    val id: String,
    val timeRecord: String,
    var status: String,
    val date: String,
)

fun TimeRecordDomain.mapToDTO(): TimeRecordDTO {
    return TimeRecordDTO (
      rid =this.rid,
        id = this.id,
        timeRecord = this.timeRecord,
        status = this.status,
        date = this.date
    )
}