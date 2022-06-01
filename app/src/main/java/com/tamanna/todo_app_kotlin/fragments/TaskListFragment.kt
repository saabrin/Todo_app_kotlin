package com.tamanna.todo_app_kotlin.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tamanna.todo_app_kotlin.R
import com.tamanna.todo_app_kotlin.adapter.TodoAdapter
import com.tamanna.todo_app_kotlin.databinding.FragmentTaskListBinding
import com.tamanna.todo_app_kotlin.swipe.SwipeToDelete
import com.tamanna.todo_app_kotlin.viewmodel.TodoViewModel

// Class extending fragment
class TaskListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var todoViewModel: TodoViewModel

    // for binding
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val todoAdapter: TodoAdapter by lazy { TodoAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        // Initializing recycler adapter
        val adapter = TodoAdapter()
        // Recyclerview
        val taskListView = binding.taskListView
        taskListView.adapter = adapter
        taskListView.layoutManager = LinearLayoutManager(requireContext())

        // TodoViewModel get data
        todoViewModel = ViewModelProvider(requireActivity())[TodoViewModel::class.java]
        todoViewModel.allData.observe(viewLifecycleOwner) { todoData ->
            adapter.setTask(todoData)
        }

        // Add btn to navigate to add
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
        }


        // Button to delete all tasks
        binding.deleteAllBtn.setOnClickListener {
            deleteAll()
        }

        // Swipe to delete each task
        val swipeDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoViewModel.deleteTask(adapter.getTaskPos(viewHolder.bindingAdapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeDelete)
        itemTouchHelper.attachToRecyclerView(taskListView)

        // searchView
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this@TaskListFragment)

        return binding.root
    }

    private fun deleteAll() {
        // Alert function
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Delete") { _, _ ->
            todoViewModel.deleteAll()
        }
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.setTitle("Are you sure to delete all tasks?")
        builder.create().show()
    }

    // SearchView functions
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchTasks(query)
        }
        return true
    }

    private fun searchTasks(query: String) {

        val searchQuery = "%$query%"
        binding.taskListView.adapter = todoAdapter

        todoViewModel.searchDatabase(searchQuery).observe(this) { todoList ->
            todoList.let {
                todoAdapter.setTask(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}