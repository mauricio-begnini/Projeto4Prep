package com.example.projeto4prep.addeditscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.projeto4prep.data.Task

@Composable
fun AddEditScreen(
    viewModel: AddEditScreenViewModel,
    navController: NavController,
    id: Int,
    getTask: (Int) -> Task,
    onInsert: (String, Boolean) -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (Task) -> Unit,
) {
    if (id != -1)
        viewModel.initTask(id, getTask)
    else
        viewModel.cleanData()

    val name by viewModel.name.observeAsState("")
    val isImportant by viewModel.isImportant.observeAsState(false)
    val showDeleteDialog by viewModel.showDeleteDialog.observeAsState(false)

    if (showDeleteDialog)
        DeleteDialog(
            closeDialog = { viewModel.showDeleteDialog.value = false },
            onDelete = {
                viewModel.onDeleteTask(onDelete)
                viewModel.showDeleteDialog.value = false
                navController.navigate("taskscreen") {
                    popUpTo("taskscreen")
                }
            }
        )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Surface() {
            InsertForm(
                name = name,
                isImportant = isImportant,
                onNameChange = { viewModel.name.value = it },
                onIsImportantChange = { viewModel.isImportant.value = it },
            )
        }
        Surface() {
            DeleteConfirmFab(
                id = id,
                onDelete = {
                    viewModel.showDeleteDialog.value = true
                },
                onUpdate = {
                    viewModel.onUpdateTask(onUpdate)
                    navController.navigate("taskscreen") {
                        popUpTo("taskscreen")
                    }
                },
                onInsert = {
                    viewModel.onInsertTask(onInsert)
                    navController.navigate("taskscreen") {
                        popUpTo("taskscreen")
                    }
                }
            )
        }
    }
}

@Composable
fun DeleteConfirmFab(
    id: Int,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    onInsert: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (id != -1) Arrangement.SpaceBetween else Arrangement.End
    ) {
        if (id != -1)
            FloatingActionButton(
                onClick = onDelete
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        FloatingActionButton(
            onClick = {
                if (id == -1)
                    onInsert()
                else
                    onUpdate()
            }
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm")
        }
    }
}

@Composable
fun DeleteDialog(
    closeDialog: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = closeDialog,
        title = { Text(text = "Delete Task") },
        text = { Text(text = "Are you sure you want to delete this task?") },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = closeDialog) {
                Text(text = "Cancel")
            }
        },
    )
}

@Composable
fun InsertForm(
    name: String,
    isImportant: Boolean,
    onNameChange: (String) -> Unit,
    onIsImportantChange: (Boolean) -> Unit,
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
    }
}