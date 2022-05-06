package com.example.projeto4prep

import android.app.Application
import com.example.projeto4prep.data.TaskDatabase

class TodoApplication: Application() {

    val database: TaskDatabase by lazy {
        TaskDatabase.getDatabase(this)
    }

}