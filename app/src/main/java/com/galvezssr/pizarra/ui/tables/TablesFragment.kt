package com.galvezssr.pizarra.ui.tables

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.galvezssr.pizarra.kernel.showAlert
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
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

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = TablesViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        val viewModel: TablesViewModel by viewModels()

        adapter = TablesAdapter { table -> navigateToTasksActivity(table) }
        binding.tableRecycler.adapter = adapter

        // Creating the new toolbar with the three points menu and title
        binding.toolbar.title = "Tablas"
        app.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

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

                        if (it.isEmpty()) {
                            binding.emptyTextView.text = "No hay tablas, cree su primera tabla"
                        } else {
                            binding.emptyTextView.text = ""
                        }
                    }

                }
            }

        }
    }

    /** Use this methods to inflate a menu. This one creates the menu taking the menu of res/menu/main_menu **/
    @Deprecated("Deprecated in Java, not in Kotlin")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /** And this one set the listeners for each button **/
    @Deprecated("Deprecated in Java, not in Kotlin")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logoff -> {
                app.showAlert("Info", "Disponible proximamente")
                return true
            }
            R.id.extra -> {
                navigateToExtraActivity()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToExtraActivity() {
        findNavController().navigate(
            R.id.action_tablesFragment_to_extraFragment
        )
    }

    private fun navigateToCreateTableFragment() {
        val dialog = CreateTableDialog()

        dialog.show(app.supportFragmentManager, "create_table_view")
    }

    private fun navigateToTasksActivity(table: Table) {
        val intent = Intent(app, TasksActivity::class.java).apply {
            putExtra("tableName", table.name)
        }

        startActivity(intent)
    }

}