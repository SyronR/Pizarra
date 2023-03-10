package com.galvezssr.pizarra.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.CreateTableViewBinding

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

    }
}