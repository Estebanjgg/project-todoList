package com.tuempresa.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val priority: String, // "Alta", "Media", "Baja"
    val isCompleted: Boolean = false
)
