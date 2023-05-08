package com.galvezssr.pizarra.ui.tasks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.pizarra.DetailTaskActivity
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.TasksViewBinding
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.Task
import com.galvezssr.pizarra.kernel.adapters.TasksAdapter
import kotlinx.coroutines.launch

class TasksFragment: Fragment(R.layout.tasks_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var tableName: String
    private lateinit var adapter: TasksAdapter

    private lateinit var binding: TasksViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = TasksViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        tableName = app.intent.extras!!.getString("tableName").toString()
        val viewModel: TasksViewModel by viewModels { TasksViewModelFactory(tableName) }

        adapter = TasksAdapter { task -> navigateToDetailTaskActivity(task) }
        binding.tasksRecycler.adapter = adapter

        /** Set the listener for the button **/
        binding.floatingActionButton.setOnClickListener {
            navigateToCreateTaskFragment()
        }

        /** Set the recycler in the XML **/

        // Observe the progress bar to modify it in the XML
        viewModel.progressBar.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        // Observe the adapter to modify it in the XML
        viewModel.tasksList.observe(viewLifecycleOwner) {

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {

                    viewModel.tasksList.value!!.collect {
                        adapter.tasks = it
                        adapter.notifyDataSetChanged()
                    }

                }
            }

        }

        /** Swipe to delete **/
        swipeToDelete(view)
    }


    /** Now implementing the function that will take care of swiping to delete **/
    private fun swipeToDelete(view: View) {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.tasks[position]

                FirebaseFirestore.deleteTaskFromTable(task, Table(tableName), view)
            }

        }).attachToRecyclerView(binding.tasksRecycler)
    }


    private fun navigateToCreateTaskFragment() {
        findNavController().navigate(
            R.id.action_tasksFragment_to_createTasksFragment
        )
    }

    private fun navigateToDetailTaskActivity(task: Task) {
        val intent = Intent(app, DetailTaskActivity::class.java).apply {
            putExtra("tableName", tableName)
            putExtra("taskName", task.name)
            putExtra("taskDescription", task.description)
            putExtra("taskPriority", task.priority)
            putExtra("taskDate", task.date)
        }

        startActivity(intent)
    }

}