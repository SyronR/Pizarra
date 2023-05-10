package com.galvezssr.pizarra.ui.tables

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.CreateTableViewBinding
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.showAlert

class CreateTableDialog: DialogFragment(R.layout.create_table_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

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

        /** Set the listener for the button **/
        binding.buttomCreate.setOnClickListener {
            if (binding.textName.text.isNotEmpty()) {

//                if (!checkEqualTables(binding.textName.text.toString()))
//                    FirebaseFirestore.createTable( Table(binding.textName.text.toString()), app )
//
//                else
//                    app.showAlert("Error", "Ya hay una tabla con el mismo nombre")

                FirebaseFirestore.createTable( Table(binding.textName.text.toString()), app )

                /** This function close the Dialog when the button 'buttomCreate' is pressed **/
                dismiss()
            } else {
                app.showAlert("Advertencia", "Una tabla no puede no tener nombre")
            }
        }
    }

//    /** Returns false if there are no tables with the same name **/
//    private fun checkEqualTables(tableName: String): Boolean {
//        val tables = FirebaseFirestore.getTables()
//
//        for (table in tables) {
//            if (tableName == table.name)
//                return true
//        }
//
//        return false
//    }
}