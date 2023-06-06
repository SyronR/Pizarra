package com.galvezssr.pizarra.kernel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.kernel.objects.Task

class TasksAdapter(val listener: (Task) -> Unit): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    var tasks = emptyList<Task>()

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.name.text = task.name

        /** Set the image color based on the priority **/
//        if (task.priority == "3") {
//            holder.image.setImageResource(R.drawable.red)
//
//        } else if (task.priority == "2") {
//            holder.image.setImageResource(R.drawable.yellow)
//
//        } else {
//            holder.image.setImageResource(R.drawable.green)
//
//        }

        when (task.priority) {
            "3" -> holder.image.setImageResource(R.drawable.red)
            "2" -> holder.image.setImageResource(R.drawable.yellow)
            else -> holder.image.setImageResource(R.drawable.green)
        }

        holder.itemView.setOnClickListener {
            listener(task)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.imageView)
    }
}