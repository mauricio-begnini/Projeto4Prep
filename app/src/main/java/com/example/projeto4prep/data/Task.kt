package com.example.projeto4prep.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isImportant: Boolean,
    val isCompleted: Boolean = false,
)