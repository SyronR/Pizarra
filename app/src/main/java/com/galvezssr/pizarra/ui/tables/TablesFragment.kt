package com.galvezssr.pizarra.ui.tables

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
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.TasksActivity
import com.galvezssr.pizarra.databinding.TablesViewBinding
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.adapters.TablesAdapter
import kotlinx.coroutines.launch

class TablesFragment: Fragment(R.layout.tables_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var adapter: TablesAdapter

    private lateinit var binding: TablesViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = TablesViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        val viewModel: TablesViewModel by viewModels()

        adapter = TablesAdapter { table -> navigateToTasksActivity(table) }
        binding.tableRecycler.adapter = adapter

        /** Set the listener for the button **/
        binding.floatingActionButton.setOnClickListener {
            navigateToCreateTableFragment()
        }

        /** Set the recycler **/

        // Observe the progress bar to modify it in the XML
        viewModel.progressBar.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        // Observe the adapter to modify it in the XML
        viewModel.tablesList.observe(viewLifecycleOwner) {

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {

                    viewModel.tablesList.value!!.collect {
                        adapter.tables = it
                        adapter.notifyDataSetChanged()
                    }

                }
            }

        }
    }

    private fun navigateToCreateTableFragment() {
        findNavController().navigate(
            R.id.action_tablesFragment_to_createTableFragment
        )
    }

    private fun navigateToTasksActivity(table: Table) {
        val intent = Intent(app, TasksActivity::class.java).apply {
            putExtra("tableName", table.name)
        }

        startActivity(intent)
    }

}