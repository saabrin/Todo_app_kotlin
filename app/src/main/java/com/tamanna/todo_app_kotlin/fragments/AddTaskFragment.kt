package com.tamanna.todo_app_kotlin.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tamanna.todo_app_kotlin.R
import com.tamanna.todo_app_kotlin.data.TodoData
import com.tamanna.todo_app_kotlin.databinding.FragmentAddTaskBinding
import com.tamanna.todo_app_kotlin.viewmodel.TodoViewModel

class AddTaskFragment : Fragment() {

    // For binding the view
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        // Set up viewModel Provider
        todoViewModel = ViewModelProvider(requireActivity())[TodoViewModel::class.java]

        // Set up listener to save button
        binding.saveTaskBtn.setOnClickListener{
            addTask()
        }

        // Button to get back to task list
        binding.addBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_addTaskFragment_to_taskListFragment)
        }

        return binding.root
    }

    // add task function
    private fun addTask() {
        // Get values from input
        val title = binding.titleInput.text.toString()
        val task = binding.taskInput.text.toString()

        // if sats to add task to list
        if(userInput(title,task)) {
            // add task - insert data to db
            val newTask = TodoData(0, title, task)
            todoViewModel.insertTask(newTask)
            Toast.makeText(context, "Your task has been added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTaskFragment_to_taskListFragment)
        } else{
            // Message to user if inputs are empty
            Toast.makeText(context, "Please fill in both fields!", Toast.LENGTH_SHORT).show()
        }

    }

    // function to check if inputs empty or not
    private fun userInput(title: String, task: String) : Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(task)){
            false
        } else !(title.isEmpty() || task.isEmpty())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}