
package com.galvezssr.pizarra.kernel

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
@Suppress("DEPRECATION")
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

    // GET
    private fun getDDBB(): FirebaseFirestore = FirebaseFirestore.getInstance()

    private fun getCurrentUser(): String = Firebase.auth.currentUser?.email!!

//    /** Get all tables from the current user **/
//    fun getTables(): List<Table> {
//        val tableList = mutableListOf<Table>()
//
//        FirebaseFirestore.getInstance().collection(USER_COLECCTION).document(getCurrentUser()).collection(
//            TABLES_COLECCTION).get().addOnSuccessListener {
//                for (document in it) {
//                    val table = document.toObject(Table::class.java)
//                    tableList.add(table)
//                }
//        }
//
//        return tableList
//    }

    fun getTablesFlow(): Flow<List<Table>> {
        return FirebaseFirestore.getInstance().collection(USER_COLECCTION)
            .document(getCurrentUser()).collection(TABLES_COLECCTION)
            .orderBy("name", Query.Direction.DESCENDING).snapshots().map {
                it.toObjects(Table::class.java)
            }
    }

    fun getTasksFromTableFlow(table: Table): Flow<List<Task>> {
        return FirebaseFirestore.getInstance().collection(USER_COLECCTION)
            .document(getCurrentUser()).collection(TABLES_COLECCTION)
            .document(table.name).collection(TASKS_COLECCTION)
            .orderBy("priority", Query.Direction.DESCENDING).snapshots().map {
                it.toObjects(Task::class.java)
            }
    }

    // CREATE
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
            if (it.isSuccessful) {
                app.showAlert("Info", "Usuario creado correctamente")
                forceBack(app)
            } else
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
                if (it.isSuccessful) {
                    app.showAlert("Info", "Tabla creada correctamente")
                } else
                    app.showAlert("Error", "No se ha podido crear la tabla")
        }
    }

    fun createTask(task: Task, table: Table, app: AppCompatActivity) {
        val ddbb = getDDBB()
        val currentUser = getCurrentUser()
        val taskMap = hashMapOf(
            "name" to task.name,
            "description" to task.description,
            "priority" to task.priority,
            "date" to task.date
        )

        ddbb.collection(USER_COLECCTION).document(currentUser).collection(TABLES_COLECCTION).document(table.name).collection(
            TASKS_COLECCTION).document(task.name).set(taskMap).addOnCompleteListener {

                if (it.isSuccessful) {
                    app.showAlert("Info", "Tarea creada correctamente")
                    forceBack(app)
                } else
                    app.showAlert("Error", "No se ha podido crear la tarea")

        }
    }

    // EDIT

    fun editTask(task: Task, table: Table, app: AppCompatActivity) {
        val ddbb = getDDBB()
        val currentUser = getCurrentUser()
        val taskMap = hashMapOf(
            "name" to task.name,
            "description" to task.description,
            "priority" to task.priority,
            "date" to task.date
        )

        ddbb.collection(USER_COLECCTION).document(currentUser).collection(TABLES_COLECCTION).document(table.name).collection(
            TASKS_COLECCTION).document(task.name).set(taskMap).addOnCompleteListener {

            if (it.isSuccessful) {
                app.showAlert("Info", "Tarea modificada correctamente")
                forceBack(app)
            } else
                app.showAlert("Error", "No se ha podido modificar la tarea")

        }
    }

    // DELETE

    fun deleteTable(table: Table, view: View) {
        val ddbb = getDDBB()
        val currentUser = getCurrentUser()

        /** Delete the table and displays a snackbar **/
        ddbb.collection(USER_COLECCTION).document(currentUser).collection(TABLES_COLECCTION).document(table.name).delete()
        view.showSnackBar("Tabla borrada correctamente")
    }

    fun deleteTaskFromTable(task: Task, table: Table, view: View) {
        val ddbb = getDDBB()
        val currentUser = getCurrentUser()
        val taskMap = hashMapOf(
            "name" to task.name,
            "description" to task.description,
            "priority" to task.priority,
            "date" to task.date
        )

        /** Delete the task from the table, and displays a snackbar with an action that allows you to undo the deletion **/
        ddbb.collection(USER_COLECCTION).document(currentUser).collection(TABLES_COLECCTION).document(table.name).collection(
            TASKS_COLECCTION).document(task.name).delete()

        // Make a snackbar with the action
        Snackbar.make(view, "Tarea borrada correctamente", Snackbar.LENGTH_LONG).apply { setAction("Deshacer") {

            ddbb.collection(USER_COLECCTION).document(currentUser).collection(TABLES_COLECCTION).document(table.name).collection(
                TASKS_COLECCTION).document(task.name).set(taskMap)

         }}.show()

    }

    private fun forceBack(app: AppCompatActivity) {
        /** I simulate pressing back, forcing the system to return to the previous fragment **/
        app.onBackPressed()
    }
}