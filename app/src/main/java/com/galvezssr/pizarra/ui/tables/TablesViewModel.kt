package com.galvezssr.pizarra.ui.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezssr.pizarra.kernel.FirebaseFirestore
import com.galvezssr.pizarra.kernel.Table
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TablesViewModel: ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> = _progressBar

    private val _tablesList = MutableLiveData<Flow<List<Table>>>()
    val tablesList: LiveData<Flow<List<Table>>> = _tablesList

    ////////////////////////////////////////////////////
    // FUNCTIONS ///////////////////////////////////////
    ////////////////////////////////////////////////////

    init {

        /** When this class is called, what it does is get all the tables of the logged in user,
         * but first it sets the progress bar to loading, and when it has retrieved all the tasks,
         * it changes the progress bar to finished. **/
        viewModelScope.launch {

            _progressBar.postValue(true)
            _tablesList.postValue(FirebaseFirestore.getTables())
            _progressBar.postValue(false)
        }

    }
}

