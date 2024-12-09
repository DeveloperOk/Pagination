package com.enterprise.pagination

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var appRepository = AppRepository()

    var mutableStateListItems = mutableStateListOf<String>()

    //How many elements loading new elements will start before the end of the list is reached,
    //Thanks to this number, progress bar will not be shown,
    //lazy column will be perceived as infinite
    //Choose smaller than pageSize
    var numberOfElementsFromTheEndOfList = 40
    private var startIndex = 1
    private var pageSize = 50

    var isLoading = false

    init {
        viewModelScope.launch(Dispatchers.IO) {

            loadNexItems()

        }
    }

    suspend fun loadNexItems(){

        isLoading = true

        var tempList = appRepository.getItems(startIndex, pageSize)

        mutableStateListItems.addAll(tempList)

        startIndex = startIndex + pageSize

        isLoading = false

    }



}