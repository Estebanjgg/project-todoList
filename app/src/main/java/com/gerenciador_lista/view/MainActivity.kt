package com.tuempresa.todolist.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuempresa.todolist.databinding.ActivityMainBinding
import com.tuempresa.todolist.model.Task
import com.gerenciador_lista.view.adapters.TaskAdapter
import com.tuempresa.todolist.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private val adapter = TaskAdapter({ task -> editTask(task) }, { task -> updateTask(task) })

    private val addEditTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == RESULT_OK && data != null) {
            val task = data.getSerializableExtra(AddEditTaskActivity.EXTRA_TASK) as Task
            if (task.id == 0) {
                taskViewModel.insert(task)
            } else {
                taskViewModel.update(task)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        // Configurar ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(this, { tasks ->
            adapter.setTasks(tasks)
        })

        taskViewModel.progress.observe(this, { progress ->
            binding.progressBar.progress = progress
        })

        // Evento del FloatingActionButton
        binding.fabAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            addEditTaskLauncher.launch(intent)
        }
    }

    private fun editTask(task: Task) {
        val intent = Intent(this, AddEditTaskActivity::class.java).apply {
            putExtra(AddEditTaskActivity.EXTRA_TASK, task)
        }
        addEditTaskLauncher.launch(intent)
    }

    private fun updateTask(task: Task) {
        taskViewModel.update(task)
    }
}
