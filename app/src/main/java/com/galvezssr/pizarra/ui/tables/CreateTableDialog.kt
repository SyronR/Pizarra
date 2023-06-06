package com.galvezssr.pizarra.ui.tables

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.CreateTableViewBinding
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.objects.Table
import com.galvezssr.pizarra.kernel.showAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CreateTableDialog: DialogFragment(R.layout.create_table_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var tables: Flow<List<Table>>

    private lateinit var binding: CreateTableViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = CreateTableViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        tables = FirebaseFirestore.getTablesFlow()

        /** Set the listener for the button **/
        binding.buttomCreate.setOnClickListener {
            if (binding.textName.text.isNotEmpty()) {

                lifecycleScope.launch {
                    tables.collect {

                        /** If not contains the ownTable, this code will create the table **/
                        if (!it.contains( Table(binding.textName.text.toString()) )) {
                            FirebaseFirestore.createTable( Table(binding.textName.text.toString()), app )

                        } else {
                            app.showAlert("Error", "Ya existe una tabla con el mismo nombre")

                        }
                    }
                }

                /** This function close the Dialog when the button 'buttomCreate' is pressed **/
                dismiss()
            } else {
                app.showAlert("Advertencia", "Una tabla no puede no tener nombre")
            }
        }
    }
}