package com.example.projeto4prep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto4prep.tasklistscreen.TaskListScreen
import com.example.projeto4prep.tasklistscreen.TaskListViewModel
import com.example.projeto4prep.tasklistscreen.TaskListViewModelFactory
import com.example.projeto4prep.ui.theme.Projeto4PrepTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskListViewModel: TaskListViewModel by viewModels<TaskListViewModel>{
            TaskListViewModelFactory((this.applicationContext as TodoApplication).database.taskDao())
        }
        setContent {
            Projeto4PrepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TaskListScreen(taskListViewModel = taskListViewModel)
                }
            }
        }
    }
}

