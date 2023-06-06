package com.galvezssr.pizarra.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.ChangeTaskViewBinding
import com.galvezssr.pizarra.kernel.*
import com.galvezssr.pizarra.kernel.objects.Table
import com.galvezssr.pizarra.kernel.objects.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChangeTaskDialog: DialogFragment(R.layout.change_task_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var task: Task
    private lateinit var table: Table
    private lateinit var tablesFlow: Flow<List<Table>>
    private lateinit var tables: MutableList<Table>

    private lateinit var binding: ChangeTaskViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        val bundle = arguments
        table = Table(
            bundle?.getString("tableName").toString()
        )
        task = Task(
            bundle?.getString("taskName").toString(),
            bundle?.getString("taskDescription").toString(),
            bundle?.getString("taskPriority").toString(),
            bundle?.getString("taskDate").toString()
        )

        binding = ChangeTaskViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        tablesFlow = FirebaseFirestore.getTablesFlow()
        tables = mutableListOf()


        /** Set the Spinner data **/
        lifecycleScope.launch {
            tablesFlow.collect {

                // It is important to note that since we are passing it a list
                // of 'Table' objects, we need to change the 'toString' function
                // in the same class to display the name correctly in the spinner.

                tables.addAll(it)
                tables.remove(table)

                val adapter = ArrayAdapter(app, android.R.layout.simple_spinner_item, tables)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.spinner.adapter = adapter

            }
        }

        /** Set the listener for the button **/
        binding.changeButton.setOnClickListener {
            val selectedTable = binding.spinner.selectedItem as? Table

            if (selectedTable != null) {
                val alertDialogBuilder = AlertDialog.Builder(app)

                alertDialogBuilder.setMessage("¿Desea cambiar de tabla la tarea?")
                alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
                    changeTaskFromTable(selectedTable)
                }

                alertDialogBuilder.setNegativeButton("No") {_, _ ->

                }

                alertDialogBuilder.create().show()

            } else {
                app.showAlert("Error", "Seleccione una tabla para poder cambiar la tarea")
            }
        }
    }

    private fun changeTaskFromTable(selectedTable: Table) {
        FirebaseFirestore.changeTaskFromTrable(task ,table, selectedTable, app)

        dismiss()
    }
}