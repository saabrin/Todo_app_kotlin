package com.tamanna.todo_app_kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM task_table WHERE task LIKE :searchQuery OR title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<TodoData>>

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun allData(): LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(todoData: TodoData)

    @Update
    suspend fun updateTask(todoData: TodoData)

    @Delete
    suspend fun deleteTask(todoData: TodoData)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}