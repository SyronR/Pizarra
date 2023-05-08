package com.galvezssr.pizarra.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galvezssr.pizarra.R
import com.galvezssr.pizarra.databinding.EditTaskViewBinding
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.Task

@Suppress("DEPRECATION")
class EditTaskFragment: Fragment(R.layout.edit_task_view) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var table: Table
    private lateinit var task: Task
    private lateinit var modifiedTask: Task

    private lateinit var description: String
    private lateinit var priority: String
    private lateinit var date: String

    private lateinit var datePicker: DatePickerFragment
    private lateinit var binding: EditTaskViewBinding
    private lateinit var app: AppCompatActivity

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Initialize the variables **/
        val bundle = arguments
        binding = EditTaskViewBinding.bind(view)
        app = (requireActivity() as AppCompatActivity)
        table = Table(
            bundle?.getString("tableName").toString()
        )
        task = Task(
            bundle?.getString("taskName").toString(),
            bundle?.getString("taskDescription").toString(),
            bundle?.getString("taskPriority").toString(),
            bundle?.getString("taskDate").toString()
        )
        // Creating the new toolbar
        binding.toolbar.title = "Tarea: ${task.name}"
        app.setSupportActionBar(binding.toolbar)

        val viewModel: EditTaskViewModel by viewModels { EditTaskViewModelFactory(task) }

        /** Set the data in the XML **/
        viewModel.dataTask.observe(viewLifecycleOwner) {
            binding.fieldDescription.setText(it.description)

            when (it.priority) {
                "3" -> binding.radioButtonHigh.isChecked = true
                "2" -> binding.radioButtonMedium.isChecked = true
                else -> binding.radioButtonLow.isChecked = true
            }

            binding.fieldDate.setText(it.date)

            priority = it.priority
            date = it.date
        }

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

        /** Set the listeners for the fieldDate **/
        binding.fieldDate.setOnClickListener {
            datePicker = DatePickerFragment {day, month, year ->  onDateSelected(day, month, year)}
            datePicker.show(app.supportFragmentManager, "datePicker")
        }

        /** When the button 'cancel' is pressed, return to the previous fragment **/
        binding.cancelButton.setOnClickListener {
            forceBack()
        }

        /** When the button 'save' is pressed, set the new data in the DB **/
        binding.saveButton.setOnClickListener {

            description = binding.fieldDescription.text.toString()

            modifiedTask = Task(
                task.name,
                description,
                priority,
                date
            )

            FirebaseFirestore.editTask(modifiedTask, table, app)

        }
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        date = "$day-$month-$year"
        binding.fieldDate.setText(date)
    }

    private fun forceBack() {
        app.onBackPressed()
    }

//    private fun finishActivity() {
//        activity?.finish()
//    }

}