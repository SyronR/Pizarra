package com.galvezssr.pizarra.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.RegisterViewBinding
import com.galvezssr.pizarra.kernel.*
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment: Fragment(R.layout.register_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var repetedPassword: String
    private lateinit var user: User

    private lateinit var binding: RegisterViewBinding
    private lateinit var app: AppCompatActivity

    private val auth = FirebaseAuth.getInstance()

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = RegisterViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)

        /** Set the Listeners for the buttons **/
        binding.buttonRegister.setOnClickListener {
            registerWithApp(view)
        }
    }

    private fun registerWithApp(view: View) {

        /** If all the fields are filled, it will enter **/
        if (binding.campoNombre.text.isNotEmpty() && binding.campoTelefono.text.isNotEmpty() &&
            binding.campoEmail.text.isNotEmpty() && binding.campoPassword.text.isNotEmpty() &&
            binding.campoPassword2.text.isNotEmpty()) {

            name = binding.campoNombre.text.toString()
            phone = binding.campoTelefono.text.toString()
            email = binding.campoEmail.text.toString()
            password = binding.campoPassword.text.toString()
            repetedPassword = binding.campoPassword2.text.toString()

            user = User(name, phone, email, password)

            /** If the 2 passwords are the same, then... **/
            if (password == repetedPassword) {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    /** If the result has been successful... **/
                    if (it.isSuccessful) {
                        FirebaseFirestore.createUser(user, app)

                    } else {
                        app.showAlert("Error", "Ya existe un usuario con el mismo email")
                    }
                }

            } else
                view.showSnackBar("Las contraseñas son diferentes, intentalo de nuevo")

        } else
            view.showEmptySnackBar()
    }
}