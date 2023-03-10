package com.galvezssr.pizarra.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.TablesViewBinding

class TablesFragment: Fragment(R.layout.tables_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var binding: TablesViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = TablesViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)

        /** Set the listener for the button **/
        binding.floatingActionButton.setOnClickListener {
            navigateToCreateTableFragment()
        }

        /** Prepare the recycler **/
        // TODO: Make the recycler
    }

    private fun navigateToCreateTableFragment() {
        findNavController().navigate(
            R.id.action_tablesFragment_to_createTableFragment
        )
    }

}