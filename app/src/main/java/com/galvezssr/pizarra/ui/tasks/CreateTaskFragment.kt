package com.galvezssr.pizarra.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.CreateTaskViewBinding
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.Task

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
            task = Task(binding.fieldName.text.toString(), binding.fieldDescription.text.toString(), priority, date)
            table = Table(tableName)

            FirebaseFirestore.createTask(task, table, app)
        }
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        date = "$day-$month-$year"
        binding.fieldDate.setText(date)
    }
}