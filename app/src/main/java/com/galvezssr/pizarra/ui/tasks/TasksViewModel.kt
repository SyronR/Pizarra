package com.galvezssr.pizarra.ui.tasks

import androidx.lifecycle.*
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import com.galvezssr.pizarra.kernel.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TasksViewModel(private val tableName: String): ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> = _progressBar

    private val _tasksList = MutableLiveData<Flow<List<Task>>>()
    val tasksList: LiveData<Flow<List<Task>>> = _tasksList

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////
    init {

        viewModelScope.launch {
            _progressBar.postValue(true)
            _tasksList.postValue(FirebaseFirestore.getTasksFromTable( Table(tableName) ))
            _progressBar.postValue(false)
        }

    }

}

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(private val tableName: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(tableName) as T
    }
}