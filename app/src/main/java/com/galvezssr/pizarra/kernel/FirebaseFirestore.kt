package com.galvezssr.pizarra.kernel

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object FirebaseFirestore {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private const val USER_COLECCTION = "users"
    private const val TABLES_COLECCTION = "tables"
//    private const val TASKS_COLECCTION = "tasks"

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private fun getDDBB(): FirebaseFirestore = FirebaseFirestore.getInstance()

    private fun getCurrentUser(): String = Firebase.auth.currentUser?.email!!

    /** Get all tables from the current user **/
    fun getTables(): Flow<List<Table>> {
        return FirebaseFirestore.getInstance().collection(USER_COLECCTION)
            .document(getCurrentUser()).collection(TABLES_COLECCTION)
            .orderBy("name", Query.Direction.DESCENDING).snapshots().map {
                it.toObjects(Table::class.java)
            }
    }

    fun createUser(user: User, app: AppCompatActivity) {
        val ddbb = getDDBB()
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

    fun createTable(table: Table, app: AppCompatActivity) {
        val ddbb = getDDBB()
        val currentUser = getCurrentUser()
        val tableMap = hashMapOf(
            "name" to table.name
        )

        ddbb.collection(USER_COLECCTION).document(currentUser)
            .collection(TABLES_COLECCTION).document(table.name).set(tableMap).addOnCompleteListener {

                /** If the result has been successful... **/
                if (it.isSuccessful)
                    app.showAlert("Info", "Tabla creada correctamente")
                else
                    app.showAlert("Error", "No se ha podido crear la tabla")
        }
    }
}