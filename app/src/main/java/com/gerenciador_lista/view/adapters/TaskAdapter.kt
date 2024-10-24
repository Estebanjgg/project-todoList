package com.gerenciador_lista.view.adapters

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuempresa.todolist.databinding.ItemTaskBinding
import com.tuempresa.todolist.model.Task

class TaskAdapter(
    private val onTaskClicked: (Task) -> Unit,
    private val onTaskChecked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                textViewTaskTitle.text = task.title
                textViewPriority.text = task.priority
                checkBoxCompleted.isChecked = task.isCompleted

                // Establecer el color según la prioridad
                val priorityColor = when (task.priority) {
                    "Alta" -> R.color.holo_red_dark
                    "Media" -> R.color.holo_orange_dark
                    else -> R.color.holo_green_dark
                }
                textViewPriority.setTextColor(root.context.getColor(priorityColor))

                root.setOnClickListener {
                    onTaskClicked(task)
                }
                checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                    onTaskChecked(task.copy(isCompleted = isChecked))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    fun setTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
