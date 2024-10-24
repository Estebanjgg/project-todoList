package com.tuempresa.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.tuempresa.todolist.database.TaskDatabase
import com.tuempresa.todolist.model.Task
import com.tuempresa.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    val progress: LiveData<Int>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks

        progress = Transformations.map(allTasks) { tasks ->
            val completed = tasks.count { it.isCompleted }
            if (tasks.isNotEmpty()) (completed * 100) / tasks.size else 0
        }
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }
}
