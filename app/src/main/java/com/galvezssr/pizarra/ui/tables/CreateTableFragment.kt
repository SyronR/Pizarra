package com.galvezssr.pizarra.ui.tables

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.CreateTableViewBinding
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table

class CreateTableFragment: Fragment(R.layout.create_table_view) {

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
        binding.buttonCreate.setOnClickListener {
            FirebaseFirestore.createTable( Table(binding.textName.text.toString()), app )
        }
    }
}