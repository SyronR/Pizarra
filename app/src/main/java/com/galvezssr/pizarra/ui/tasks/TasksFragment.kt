package com.galvezssr.pizarra.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.TasksViewBinding

class TasksFragment: Fragment(R.layout.tasks_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var tableName: String

    private lateinit var binding: TasksViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = TasksViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        tableName = app.intent.extras!!.getString("tableName").toString()
        val viewModel: TasksViewModel by viewModels { TasksViewModelFactory(tableName) }

        /** Set the listener for the button **/
        binding.floatingActionButton.setOnClickListener {
            navigateToCreateTaskFragment()
        }

        binding.floatingActionButton2.setOnClickListener {
            navigateToDetailTaskFragment()
        }

        /** Set the text in the XML **/
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textView3.text = it
        }
    }

    private fun navigateToCreateTaskFragment() {
        findNavController().navigate(
            R.id.action_tasksFragment_to_createTasksFragment
        )
    }

    private fun navigateToDetailTaskFragment() {
        val intent = Intent(app, DetailTaskActivity::class.java).apply {}

        startActivity(intent)
    }

}