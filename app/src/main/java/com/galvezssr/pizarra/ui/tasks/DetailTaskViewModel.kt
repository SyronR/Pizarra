package com.galvezssr.pizarra.ui.tasks

import androidx.lifecycle.*
import com.galvezssr.pizarra.kernel.Task
import kotlinx.coroutines.launch

class DetailTaskViewModel(private val task: Task): ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private val _dataTask = MutableLiveData<Task>()
    val dataTask: LiveData<Task> = _dataTask

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    init {

        viewModelScope.launch {
            _dataTask.postValue(task)
        }

    }

}

@Suppress("UNCHECKED_CAST")
class DetailTaskViewModelFactory(private val task: Task): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailTaskViewModel(task) as T
    }
}