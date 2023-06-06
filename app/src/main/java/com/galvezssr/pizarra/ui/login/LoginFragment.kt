package com.galvezssr.pizarra.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
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
import com.galvezssr.pizarra.kernel.showToast
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: Fragment(R.layout.login_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var prefs: SharedPreferences

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

        /** Check for internet connexion **/
        if (!isOnline(app)) {
            app.showToast("Sin conexión a internet")
        }

        /** Set the listeners for the buttons **/
        binding.botonAcceder.setOnClickListener {
            loginWithApp(view)
        }

//        binding.botonGoogle.setOnClickListener {
//            loginWithGoogle()
//        }

        binding.botonRegistrar.setOnClickListener {
            navigateToRegisterFragment()
        }


        /** Check for cache session data **/
        checkSession()
    }

    override fun onStart() {
        super.onStart()

        binding.loginView.visibility = View.VISIBLE
    }

    private fun checkSession() {
        prefs = app.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val prefsEmail = prefs.getString("email", null)

        // If session data exists, then the TablesFragment starts without make the login
        if (prefsEmail != null) {
            binding.loginView.visibility = View.INVISIBLE
            navigateToTablesActivity(prefsEmail)
        }
    }

    @Suppress("DEPRECATION")
    private fun isOnline(app: Context): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    private fun loginWithApp(view: View) {

        /** If all the fields are filled, it will enter **/
        if (binding.campoEmail.text.isNotEmpty() && binding.campoPassword.text.isNotEmpty()) {

            email = binding.campoEmail.text.toString()
            password = binding.campoPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                /** If the result has been successful... **/
                if (it.isSuccessful)
                    navigateToTablesActivity(email)
                else
                    app.showAlert("Error", "No se ha podido iniciar sesión")
            }

        } else
            view.showEmptySnackBar()
    }

//    private fun loginWithGoogle() {
//        app.showAlert("Info", "Disponible proximamente")
//    }

    private fun navigateToRegisterFragment() {
        findNavController().navigate(
            R.id.action_loginView_to_registerView
        )
    }

    private fun navigateToTablesActivity(email: String) {
        val intent = Intent(app, TablesActivity::class.java).apply {
            putExtra("email", email)
        }

        startActivity(intent)
    }

}