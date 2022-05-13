package com.example.projeto4prep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projeto4prep.addeditscreen.AddEditScreen
import com.example.projeto4prep.addeditscreen.AddEditScreenViewModel
import com.example.projeto4prep.tasklistscreen.*
import com.example.projeto4prep.ui.theme.Projeto4PrepTheme
import java.lang.reflect.Type

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskViewModel: TaskViewModel by viewModels<TaskViewModel> {
            TaskViewModelFactory(
                (this.applicationContext as TodoApplication).database.taskDao()
            )
        }
        val addEditViewModel: AddEditScreenViewModel by viewModels<AddEditScreenViewModel>()
        setContent {
            Projeto4PrepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TodoApp(
                        taskViewModel = taskViewModel,
                        addEditScreenViewModel = addEditViewModel
                    )
                }
            }
        }
    }

    @Composable
    fun TodoApp(
        taskViewModel: TaskViewModel,
        addEditScreenViewModel: AddEditScreenViewModel
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "taskscreen") {
            composable("taskscreen") {
                TaskScreen(taskViewModel = taskViewModel, navController = navController)
            }
            composable(
                route = "addedittask?id={id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1

                })
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                AddEditScreen(
                    viewModel = addEditScreenViewModel,
                    navController = navController,
                    id = id,
                    getTask = taskViewModel::getTask,
                    onInsert = taskViewModel::addNewTask,
                    onUpdate = taskViewModel::update,
                    onDelete = taskViewModel::delete
                )
            }
        }
    }
}

