@file:Suppress("DEPRECATION")

package com.galvezssr.pizarra.ui.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.DetailTaskViewBinding
import com.galvezssr.pizarra.kernel.Task

class DetailTaskFragment: Fragment(R.layout.detail_task_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var tableName: String

    private lateinit var task: Task

    private lateinit var binding: DetailTaskViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = DetailTaskViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        tableName = app.intent.extras!!.getString("tableName").toString()
        task = Task(
            app.intent.extras!!.getString("taskName").toString(),
            app.intent.extras!!.getString("taskDescription").toString(),
            app.intent.extras!!.getString("taskPriority").toString(),
            app.intent.extras!!.getString("taskDate").toString()
        )
        // Creating the new toolbar with the three points menu and title
        binding.toolbar.title = "Detalle de la tarea"
        app.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        val viewModel: DetailTaskViewModel by viewModels { DetailTaskViewModelFactory(task) }

        /** Set the data in the XML **/
        viewModel.dataTask.observe(viewLifecycleOwner) {
            binding.fieldName.setText(it.name)
            binding.fieldDescription.setText(it.description)

//            if (it.priority == "3")
//                binding.fieldPriority.setText("Alta")
//
//            else if (it.priority == "2")
//                binding.fieldPriority.setText("Media")
//
//            else
//                binding.fieldPriority.setText("Baja")
//

            when (it.priority) {
                "3" -> binding.fieldPriority.setText("Alta")
                "2" -> binding.fieldPriority.setText("Media")
                else -> binding.fieldPriority.setText("Baja")
            }

            binding.fieldDate.setText(it.date)
        }

    }

    /** Use this methods to inflate a menu. This one creates the menu taking the menu of res/menu/detail_menu **/
    @Deprecated("Deprecated in Java, not in Kotlin")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /** And this one set the listeners for each button **/
    @Deprecated("Deprecated in Java, not in Kotlin")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_move -> {
                navigateToChangeTaskDialog()
                return true
            }
            R.id.action_modify -> {
                navigateToEditTaskFragment()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToChangeTaskDialog() {
        val bundle = Bundle()
        val dialog = ChangeTaskDialog()

        bundle.apply {
            putString("tableName", tableName)
            putString("taskName", task.name)
            putString("taskDescription", task.description)
            putString("taskPriority", task.priority)
            putString("taskDate", task.date)
        }
        dialog.arguments = bundle
        dialog.show(app.supportFragmentManager, "change_task_view")
    }

    private fun navigateToEditTaskFragment() {
        val bundle = Bundle().apply {
            putString("tableName", tableName)
            putString("taskName", task.name)
            putString("taskDescription", task.description)
            putString("taskPriority", task.priority)
            putString("taskDate", task.date)
        }

        findNavController().navigate(
            R.id.action_detailTaskFragment_to_editTaskFragment, bundle
        )
    }

}