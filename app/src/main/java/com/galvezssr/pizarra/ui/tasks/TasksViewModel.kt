package com.galvezssr.pizarra.ui.tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TasksViewModel(private val tableName: String): ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    init {

        viewModelScope.launch {
            _text.postValue("Vista de tareas ($tableName)")
        }

    }

}

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(private val tableName: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(tableName) as T
    }
}