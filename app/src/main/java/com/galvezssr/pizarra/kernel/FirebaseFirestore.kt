package com.galvezssr.pizarra.kernel

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object FirebaseFirestore {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private const val USER_COLECCTION = "users"
    private const val TABLES_COLECCTION = "tables"
    private const val TASKS_COLECCTION = "tasks"

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private fun getBBDD(): FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUser(): String = Firebase.auth.currentUser?.email!!

    fun createUser(user: User, app: AppCompatActivity) {
        val ddbb = getBBDD()
        val userMap = hashMapOf(
            "name" to user.name,
            "phone" to user.phone,
            "email" to user.email,
            "password" to user.password
        )

        ddbb.collection(USER_COLECCTION).document(user.email).set(userMap).addOnCompleteListener {

            /** If the result has been successful... **/
            if (it.isSuccessful)
                app.showAlert("Info", "Usuario creado correctamente")
            else
                app.showAlert("Error", "Se ha producido un error al añadir el usuario a la BBDD")
        }

    }

}