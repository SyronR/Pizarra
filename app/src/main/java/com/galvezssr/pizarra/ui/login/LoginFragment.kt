package com.galvezssr.pizarra.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.TablesActivity
import com.galvezssr.pizarra.databinding.LoginViewBinding
import com.galvezssr.pizarra.kernel.showAlert
import com.galvezssr.pizarra.kernel.showEmptySnackBar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: Fragment(R.layout.login_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var email: String
    private lateinit var password: String

    private lateinit var binding: LoginViewBinding
    private lateinit var app: AppCompatActivity

    private val auth = FirebaseAuth.getInstance()

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = LoginViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)

        /** Set the listeners for the buttons **/
        binding.botonAcceder.setOnClickListener {
            loginWithApp(view)
        }

        binding.botonGoogle.setOnClickListener {
            loginWithGoogle()
        }

        binding.botonRegistrar.setOnClickListener {
            navigateToRegisterFragment()
        }

    }

    private fun loginWithApp(view: View) {

        /** If all the fields are filled, it will enter **/
        if (binding.campoEmail.text.isNotEmpty() && binding.campoPassword.text.isNotEmpty()) {

            email = binding.campoEmail.text.toString()
            password = binding.campoPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                /** If the result has been successful... **/
                if (it.isSuccessful)
                    navigateToTablesActivity()
                else
                    app.showAlert("Error", "Los creedenciales son incorrectos, intentalo de nuevo")
            }

        } else
            view.showEmptySnackBar()
    }

    private fun loginWithGoogle() {
        app.showAlert("Info", "Disponible proximamente")
    }

    private fun navigateToRegisterFragment() {
        findNavController().navigate(
            R.id.action_loginView_to_registerView
        )
    }

    private fun navigateToTablesActivity() {
        val intent = Intent(app, TablesActivity::class.java).apply {}

        startActivity(intent)
    }

}