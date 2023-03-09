@file:Suppress("unused")

package com.galvezssr.pizarra.kernel

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showEmptyToast() {
    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/** Import 'com.google.android.material:material:1.3.0' in the 'gradle.app' **/
fun View.showEmptySnackBar() {
    Snackbar.make(this, "Complete todos los campos", Snackbar.LENGTH_LONG).show()
}

fun View.showSnackBar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}

fun AppCompatActivity.showAlert(code: String, text: String) {
    val builder = AlertDialog.Builder(this)

    builder.setTitle(code)
    builder.setMessage(text)
    builder.setPositiveButton("Aceptar", null)

    builder.create().show()
}