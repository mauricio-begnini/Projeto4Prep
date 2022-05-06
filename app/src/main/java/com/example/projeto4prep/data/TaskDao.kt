package com.example.projeto4prep.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER BY important/*, _created ASC*/")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task ORDER BY important DESC, name ASC")
    fun getAllByName(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}