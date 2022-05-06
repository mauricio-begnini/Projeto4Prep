package com.example.projeto4prep.tasklistscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projeto4prep.R
import com.example.projeto4prep.data.Task


@Composable
fun TaskListScreen(
    taskListViewModel: TaskListViewModel
) {
    val taskList by taskListViewModel.allTasks.observeAsState(listOf())
    Column {
        InsertForm(addNewTask = taskListViewModel::addNewTask)
        Divider()
        TaskList(taskList, taskListViewModel::updateTask)
    }
}

@Composable
fun InsertForm(
    addNewTask: (String, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("")}
    var important by remember { mutableStateOf(false)}

    Column(){
        OutlinedTextField(value = name, onValueChange = {
            name = it
        })
        Row() {
            Text(text = "Important Task: ")
            Checkbox(
                checked = important,
                onCheckedChange = {important = !important}
            )
        }
        OutlinedButton(
            onClick = {
                if(name!= "") {
                    addNewTask(name, important)
                    name = ""
                    important = false
                }
            }
        ) {
            Text(text = "Insert Task")
        }
    }
}

@Composable
fun TaskList(
    taskList: List<Task>,
    updateTask: (Task) -> Unit
) {
    LazyColumn(){
        items(taskList){ task ->
            TaskEntry(task = task, updateTask = updateTask)
        }
    }
}

@Composable
fun TaskEntry(
    task: Task,
    updateTask: (Task) -> Unit
) {
    Row() {
        Checkbox(
            modifier = Modifier.padding(4.dp),
            checked = task.completed,
            onCheckedChange = {
                updateTask(task.copy(completed = !task.completed))
            }
        )
        Text(
            modifier = Modifier.weight(1f),
            text = task.name
        )
        if(task.important)
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.ic_priority_high),
                contentDescription = "Priority"
            )
    }
}