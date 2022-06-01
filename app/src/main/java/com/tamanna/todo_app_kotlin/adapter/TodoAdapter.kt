package com.tamanna.todo_app_kotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tamanna.todo_app_kotlin.data.TodoData
import com.tamanna.todo_app_kotlin.databinding.TaskRowBinding
import com.tamanna.todo_app_kotlin.fragments.TaskListFragmentDirections
import java.util.ArrayList

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    // Variable of a array list with typ of TodoData
    private var todoList = ArrayList<TodoData>()

    inner class TodoViewHolder(val binding: TaskRowBinding) : RecyclerView.ViewHolder(binding.root)

    // Setting the list
    @SuppressLint("NotifyDataSetChanged")
    fun setTask(todoData: List<TodoData>) {
        this.todoList = todoData as ArrayList<TodoData>
        notifyDataSetChanged()
    }

    // Getting position for items
    fun getTaskPos(position: Int): TodoData {
        return todoList[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TaskRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        // Creating new variable of item in list
        val taskItem = todoList[position]

        // Getting values from inputs
        holder.binding.taskTitle.text = taskItem.title
        holder.binding.taskDescription.text = taskItem.task

        // Long press for update
        holder.binding.taskRowLayout.setOnLongClickListener {
            val press =
                TaskListFragmentDirections.actionTaskListFragmentToUpdateTaskFragment(taskItem)
            holder.itemView.findNavController().navigate(press)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}