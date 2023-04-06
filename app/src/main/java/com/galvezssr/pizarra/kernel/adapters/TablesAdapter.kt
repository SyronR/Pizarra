package com.galvezssr.pizarra.kernel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.kernel.Table

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

        /** We create the listener so that when we click on an element of the list, it returns the object **/
        holder.itemView.setOnClickListener {
            listener(table)
        }
    }

    ////////////////////////////////////////////////////
    // MINI-CLASSES ////////////////////////////////////
    ////////////////////////////////////////////////////

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)

    }
}