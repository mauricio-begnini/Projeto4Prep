package com.example.projeto4prep.tasklistscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projeto4prep.R
import com.example.projeto4prep.data.Task


@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addedittask")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Insert")
            }
        }
    ) {
        val taskList by taskViewModel.allTasks.observeAsState(listOf<Task>())

        Column {
            TaskList(
                taskList = taskList,
                onUpdateTask = taskViewModel::update,
                navToEditTask = { task ->
                    navController.navigate("addedittask?id=${task.id}")
                }
            )
        }
    }
}


@Composable
fun TaskList(
    taskList: List<Task>,
    onUpdateTask: (Task) -> Unit,
    navToEditTask: (Task) -> Unit,
) {
    LazyColumn(){
        items(taskList){ task ->
            TaskEntry(task = task, onCompletedChange = onUpdateTask, navToEditTask = navToEditTask)
        }
    }
}

@Composable
fun TaskEntry(
    task: Task,
    onCompletedChange: (Task) -> Unit,
    navToEditTask: (Task) -> Unit,
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
            Icon(
                modifier = Modifier.clickable { navToEditTask(task) },
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
    }
}