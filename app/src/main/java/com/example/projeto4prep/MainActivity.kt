package com.example.projeto4prep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.projeto4prep.tasklistscreen.*
import com.example.projeto4prep.ui.theme.Projeto4PrepTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskViewModel: TaskViewModel by viewModels<TaskViewModel> {
            TaskViewModelFactory(
                (this.applicationContext as TodoApplication).database.taskDao()
            )
        }
        setContent {
            Projeto4PrepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TaskScreen(taskViewModel)
                }
            }
        }
    }
}

