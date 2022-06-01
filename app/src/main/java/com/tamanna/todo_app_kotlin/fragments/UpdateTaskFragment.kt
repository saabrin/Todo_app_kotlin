package com.tamanna.todo_app_kotlin.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tamanna.todo_app_kotlin.R
import com.tamanna.todo_app_kotlin.data.TodoData
import com.tamanna.todo_app_kotlin.databinding.FragmentUpdateTaskBinding
import com.tamanna.todo_app_kotlin.viewmodel.TodoViewModel

class UpdateTaskFragment : Fragment() {

    private lateinit var todoViewModel: TodoViewModel

    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateTaskFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateTaskBinding.inflate(inflater,container, false)

        // Initialize viewModel provider
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        // Setting text with Safe args
        binding.updateTitleInput.setText(args.taskItem.title)
        binding.updateTaskInput.setText(args.taskItem.task)

        // Button to update task
        binding.updateTaskBtn.setOnClickListener{
            updateTask()
        }

        // Button to get back to task list
        binding.updateBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_updateTaskFragment_to_taskListFragment)
        }

        // Button to delete task
        binding.deleteTaskBtn.setOnClickListener{
            deleteTask()
        }

        return binding.root
    }

    private fun deleteTask() {
        // Alert function
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            todoViewModel.deleteTask(args.taskItem)
            findNavController().navigate(R.id.action_updateTaskFragment_to_taskListFragment)

        }
        builder.setNegativeButton("No"){_, _ ->
        }
        builder.setTitle("Delete ${args.taskItem.title}?")
        builder.setMessage("Are you sure that you want to delete ${args.taskItem.task}?")
        builder.create().show()
    }

    private fun updateTask(){

        // Get the current values of inputs
        val title = binding.updateTitleInput.text.toString()
        val task = binding.updateTaskInput.text.toString()

        if(userInput(title, task)) {
            val taskUpdate = TodoData(args.taskItem.id, title, task)
            todoViewModel.updateTask(taskUpdate)
            findNavController().navigate(R.id.action_updateTaskFragment_to_taskListFragment)
            Toast.makeText(requireContext(), "Your task has been updated!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill in both fields!", Toast.LENGTH_SHORT).show()
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






