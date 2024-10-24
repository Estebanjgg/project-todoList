package com.tuempresa.todolist.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.tuempresa.todolist.R
import com.tuempresa.todolist.databinding.ActivityAddEditTaskBinding
import com.tuempresa.todolist.model.Task

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private var task: Task? = null

    companion object {
        const val EXTRA_TASK = "com.tuempresa.todolist.EXTRA_TASK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Spinner de prioridades
        val priorities = resources.getStringArray(R.array.priority_levels)
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        binding.spinnerPriority.adapter = adapterSpinner

        // Si se está editando una tarea
        task = intent.getSerializableExtra(EXTRA_TASK) as Task?
        task?.let {
            binding.editTextTaskTitle.setText(it.title)
            val position = priorities.indexOf(it.priority)
            binding.spinnerPriority.setSelection(position)
        }

        binding.buttonSaveTask.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        val title = binding.editTextTaskTitle.text.toString().trim()
        val priority = binding.spinnerPriority.selectedItem.toString()

        if (title.isEmpty()) {
            binding.editTextTaskTitle.error = getString(R.string.error_empty_title)
            return
        }

        val newTask = task?.copy(title = title, priority = priority) ?: Task(title = title, priority = priority)
        val intent = Intent().apply {
            putExtra(EXTRA_TASK, newTask)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
