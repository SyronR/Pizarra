package com.galvezssr.pizarra.ui.extras

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.ExtraViewBinding

class ExtraFragment: Fragment(R.layout.extra_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var binding: ExtraViewBinding

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = ExtraViewBinding.bind(view)
        binding.toolbar.title = "Acerca de 'Pizarra'"
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        /** Set the values in the XML **/
        binding.fieldName.setText("Alberto Gálvez (Syron)")
        binding.fieldDescription.setText("1.0 (Semillas)")
        binding.fieldLanguage.setText("Kotlin / XML + Firebase")
        binding.fieldTechnology.setText(
            "Activitys + Fragments // Librería Navigation // DialogFragment // RecyclerView + Flow + SwipeToDelete // Spinners // Menu 3 puntos // RadioButtons // DatePicker // Firestore"
        )
    }
}