package com.tamanna.todo_app_kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tamanna.todo_app_kotlin.data.TodoData
import com.tamanna.todo_app_kotlin.data.TodoDatabase
import com.tamanna.todo_app_kotlin.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Extending android viewModel to use context
class TodoViewModel(application: Application): AndroidViewModel(application) {

    // variable to get database
    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository

    val allData: LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        allData = repository.getAllData
    }

    fun insertTask(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(todoData)
        }
    }

    fun updateTask(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(todoData)
        }
    }

    fun deleteTask(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(todoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String) : LiveData<List<TodoData>> {
        return repository.searchDatabase(searchQuery)
    }
}