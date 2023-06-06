package com.galvezssr.pizarra.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.CreateTaskViewBinding
import com.galvezssr.pizarra.kernel.*
import com.galvezssr.pizarra.kernel.objects.Table
import com.galvezssr.pizarra.kernel.objects.Task
import kotlinx.coroutines.flow.Flow

class CreateTaskFragment: Fragment(R.layout.create_task_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private var date = "null"
    private var priority = "1"

    private lateinit var task: Task
    private lateinit var table: Table
    private lateinit var datePicker: DatePickerFragment
    private lateinit var tableName: String

    private lateinit var tasks: Flow<List<Task>>

    private lateinit var binding: CreateTaskViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        binding = CreateTaskViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        tableName = app.intent.extras!!.getString("tableName").toString()
        table = Table(tableName)
        tasks = FirebaseFirestore.getTasksFromTableFlow( table )

        /** Set the listener for the radio buttons **/
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_low -> {
                    priority = "1"
                }
                R.id.radio_button_medium -> {
                    priority = "2"
                }
                R.id.radio_button_high -> {
                    priority = "3"
                }
            }
        }

        /** Set the listeners for the buttons **/
        binding.fieldDate.setOnClickListener {
            datePicker = DatePickerFragment {day, month, year ->  onDateSelected(day, month, year)}
            datePicker.show(app.supportFragmentManager, "datePicker")
        }

        binding.createButton.setOnClickListener {

            if (binding.fieldName.text.isNotEmpty()) {
                task = Task(binding.fieldName.text.toString(), binding.fieldDescription.text.toString(), priority, date)

                FirebaseFirestore.createTask(task, table, app)

//                lifecycleScope.launch {
//                    tasks.collect {
//
//                        /** If not contains the ownTask, this code will create the task **/
//                        if (!it.contains( task )) {
//                            FirebaseFirestore.createTask(task, table, app)
//
//                        } else {
//                            app.showAlert("Error", "Ya existe una tarea con el mismo nombre")
//
//                        }
//                    }
//                }

            } else
                view.showSnackBar("Una tarea no puede no tener nombre")

        }
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val fixedMonth = month + 1

        date = "$day-$fixedMonth-$year"
        binding.fieldDate.setText(date)
    }
}