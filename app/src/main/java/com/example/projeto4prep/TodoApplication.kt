package com.example.projeto4prep

import android.app.Application
import com.example.projeto4prep.data.TodoDatabase

class TodoApplication : Application() {

    val database: TodoDatabase by lazy {
        TodoDatabase.getInstance(this)
    }

}