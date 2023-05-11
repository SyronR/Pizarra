package com.galvezssr.pizarra.kernel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.showSnackBar

class TablesAdapter(val listener: (Table) -> Unit): RecyclerView.Adapter<TablesAdapter.ViewHolder>() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    var tables = emptyList<Table>()

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.table_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tables.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = tables[position]

        holder.name.text = table.name

        /** Create the listener so that when we click on an element of the list, it returns the object **/
        holder.itemView.setOnClickListener {
            listener(table)
        }

        /** Create the listener to delete the table in the Firebase **/
        holder.button.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)

            alertDialogBuilder.setMessage("¿Desea eliminar este elemento?")
            alertDialogBuilder.setPositiveButton("Sí") { _, _ ->
                FirebaseFirestore.deleteTable(table, holder.view)
            }

            alertDialogBuilder.setNegativeButton("No") {_, _ ->
                holder.view.showSnackBar("Borrado cancelado")
            }

            alertDialogBuilder.create().show()
        }
    }

    ////////////////////////////////////////////////////
    // MINI-CLASSES ////////////////////////////////////
    ////////////////////////////////////////////////////

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val button: ImageView = view.findViewById(R.id.delete_button)
    }
}