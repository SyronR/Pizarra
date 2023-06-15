package com.galvezssr.pizarra.kernel.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.kernel.objects.Task
import java.util.Calendar

class TasksAdapter(val listener: (Task) -> Unit): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    var tasks = emptyList<Task>()
    private var lightMode: Boolean = true

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_cardview, parent, false)
        lightMode = parent.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        val currentDate = Calendar.getInstance()

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

        /** The 'ContextCompact.getColor()' method transforms the color ID of 'R.color.orange'
         * into an integer needed by the first function **/

        if (task.date != "null") {
            val taskDate = getDateFromTask(task.date)
            val clonedTaskDate = taskDate.clone() as Calendar

            clonedTaskDate.add(Calendar.DAY_OF_MONTH, -3)
            if (clonedTaskDate <= currentDate) {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            } else {
                if (lightMode) {
                    holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                } else {
                    holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.medium_dark))
                }
            }

            if (taskDate < currentDate) {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            }
        }

        holder.itemView.setOnClickListener {
            listener(task)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.task_cardview)
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.imageView)
    }

    private fun getDateFromTask(date: String): Calendar {
        val list = date.split('-')
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(list[0]))
        calendar.set(Calendar.MONTH, (Integer.parseInt(list[1]) - 1))
        calendar.set(Calendar.YEAR, Integer.parseInt(list[2]))

        return calendar
    }
}