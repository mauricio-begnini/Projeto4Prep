package com.example.projeto4prep.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.LocalDateTime

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull val name: String,
    @NonNull val important: Boolean,
    @NonNull val completed: Boolean = false,
)/*{
    private val _created: String
        get() {
            val time = LocalDateTime.now()
            val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")
            return _created.format(formatter)
        }
}*/

