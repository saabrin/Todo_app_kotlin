package com.tamanna.todo_app_kotlin.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData: LiveData<List<TodoData>> = todoDao.allData()

    suspend fun insertTask(todoData: TodoData){
        todoDao.insertTask(todoData)
    }

    suspend fun updateTask(todoData: TodoData){
        todoDao.updateTask(todoData)
    }

    suspend fun deleteTask(todoData: TodoData){
        todoDao.deleteTask(todoData)
    }

    suspend fun deleteAll(){
        todoDao.deleteAll()
    }

    @WorkerThread
    fun searchDatabase(searchQuery: String): LiveData<List<TodoData>> {
        return todoDao.searchDatabase(searchQuery)
    }
}