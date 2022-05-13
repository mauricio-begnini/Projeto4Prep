package com.example.projeto4prep.tasklistscreen

import androidx.lifecycle.*
import com.example.projeto4prep.data.Task
import com.example.projeto4prep.data.TaskDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks().asLiveData()

    fun getTask(id: Int): Task{
        val taskList = (allTasks.value ?: listOf())
        return  taskList.find {
            id == it.id
        } ?: Task(-1, "" , isImportant = false, isCompleted = false)
    }

    private fun insert(task: Task){
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    private fun getNewTaskEntry(name: String, isImportant: Boolean): Task {
        return Task(
            name = name,
            isImportant = isImportant
        )
    }

    fun addNewTask(name: String, isImportant: Boolean) {
        val newTask = getNewTaskEntry(name, isImportant)
        insert(newTask)
    }

    fun update(task: Task){
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun delete(task: Task){
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }

}

class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(taskDao) as T
        throw IllegalArgumentException ("Unknown ViewModel Cass")
    }
}
