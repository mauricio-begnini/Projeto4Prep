package com.example.projeto4prep.addeditscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projeto4prep.data.Task

class AddEditScreenViewModel : ViewModel() {

    val id: MutableLiveData<Int> = MutableLiveData(0)
    val name: MutableLiveData<String> = MutableLiveData("")
    val isImportant: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCompleted: MutableLiveData<Boolean> = MutableLiveData(false)

    val showDeleteDialog: MutableLiveData<Boolean> = MutableLiveData(false)

    fun initTask(
        taskId: Int,
        getTask: (Int) -> Task
    ) {
        val task = getTask(taskId)
        id.value = task.id
        name.value = task.name
        isImportant.value = task.isImportant
        isCompleted.value = task.isImportant
    }

    fun cleanData(){
        id.value = -1
        name.value = ""
        isImportant.value = false
        isCompleted.value = false
    }

    fun onInsertTask(
        insertTask: (String, Boolean) -> Unit
    ) {
        insertTask(
            name.value ?: throw KotlinNullPointerException("Task name is null"),
            isImportant.value ?: throw KotlinNullPointerException("Task IsImportant is null")
        )
    }

    private fun buildTask(): Task {
        return Task(
            id.value ?: throw KotlinNullPointerException("Task Id is null"),
            name.value ?: throw KotlinNullPointerException("Task name is null"),
            isImportant.value ?: throw KotlinNullPointerException("Task IsImportant is null"),
            isCompleted.value ?: throw KotlinNullPointerException("Task isCompleted is null")
        )
    }

    fun onUpdateTask(
        updateTask: (Task) -> Unit
    ) {
        val task = buildTask()
        updateTask(task)
    }

    fun onDeleteTask(
        deleteTask: (Task) -> Unit
    ) {
        val task = buildTask()
        deleteTask(task)
    }

}