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
fun TaskScreen(
    taskViewModel: TaskViewModel
) {
    val taskList by taskViewModel.allTasks.observeAsState(listOf<Task>())

    var name by remember {
        mutableStateOf("")
    }
    var isImportant by remember {
        mutableStateOf(false)
    }

    Column {
        InsertForm(
            name = name,
            isImportant = isImportant,
            onNameChange = {name = it},
            onIsImportantChange = {isImportant = it},
            onInsert = taskViewModel::addNewTask
        )
        Divider()
        TaskList(
            taskList = taskList,
            onUpdateTask = taskViewModel::update
        )
    }
}

@Composable
fun InsertForm(
    name: String,
    isImportant: Boolean,
    onNameChange: (String) -> Unit,
    onIsImportantChange: (Boolean) -> Unit,
    onInsert: (String, Boolean) -> Unit
) {
    Column() {
        OutlinedTextField(
            label = { Text("name") },
            value = name,
            onValueChange = onNameChange
        )
        Row() {
            Text(text = "Is Important: ")
            Checkbox(
                checked = isImportant,
                onCheckedChange = onIsImportantChange
            )
        }
        OutlinedButton(onClick = {
            if(name != ""){
                onInsert(name, isImportant)
                onNameChange("")
                onIsImportantChange(false)
            }
        }) {
            Text(text = "Insert Task")
        }
    }
}

@Composable
fun TaskList(
    taskList: List<Task>,
    onUpdateTask: (Task) -> Unit,
) {
    LazyColumn(){
        items(taskList){ task ->
            TaskEntry(task = task, onCompletedChange = onUpdateTask)
        }
    }
}

@Composable
fun TaskEntry(
    task: Task,
    onCompletedChange: (Task) -> Unit
) {
    Card(modifier = Modifier.padding(4.dp)) {
        Row(){
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onCompletedChange(task.copy(isCompleted = it)) }
            )
            Text(
                modifier = Modifier.weight(1f),
                text = task.name
            )
            if(task.isImportant)
                Icon(
                    painter = painterResource(id = R.drawable.ic_priority_high),
                    contentDescription = "Is Important"
                )
        }
    }
}