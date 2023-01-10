package com.example.notesapproom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Int?,
    @ColumnInfo(name = "note") val name: String?,
)

