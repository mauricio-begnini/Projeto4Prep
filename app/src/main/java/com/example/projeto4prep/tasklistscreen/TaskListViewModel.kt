package com.example.projeto4prep.tasklistscreen

import androidx.lifecycle.*
import com.example.projeto4prep.data.Task
import com.example.projeto4prep.data.TaskDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskListViewModel(private val taskDao: TaskDao): ViewModel() {

    private val _allTasks: LiveData<List<Task>> = taskDao.getAllByName().asLiveData()

    val allTasks: LiveData<List<Task>>
        get() = _allTasks

    private fun insertTask(task: Task){
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    private fun getNewTaskEntry(taskName: String, taskImportant: Boolean): Task {
        return Task(
            name = taskName,
            important = taskImportant,
        )
    }

    fun addNewTask(taskName: String, taskImportant: Boolean){
        val newTask = getNewTaskEntry(taskName, taskImportant)
        insertTask(newTask)
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            taskDao.update(task)
        }
    }
}

class TaskListViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskListViewModel::class.java))
            return TaskListViewModel(taskDao = taskDao) as T
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}
