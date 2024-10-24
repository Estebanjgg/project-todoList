package com.tuempresa.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tuempresa.todolist.model.Task

@Dao
interface TaskDao {

    @Query("""
        SELECT * FROM task_table 
        ORDER BY 
            CASE 
                WHEN priority = 'Alta' THEN 1 
                WHEN priority = 'Media' THEN 2 
                ELSE 3 
            END, 
            isCompleted ASC
    """)
    fun getAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}
