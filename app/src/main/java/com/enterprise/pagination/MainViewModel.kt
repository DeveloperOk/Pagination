package com.enterprise.pagination

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var appRepository = AppRepository()

    var itemsList = MutableStateFlow(arrayListOf<String>())


    //How many elements loading new elements will start before the end of the list is reached,
    //Thanks to this number, progress bar will not be shown,
    //lazy column will be perceived as infinite
    //Choose smaller than pageSize
    var numberOfElementsFromTheEndOfList = 40
    private var startIndex = 1
    private var pageSize = 50

    var isLoading = MutableStateFlow(false)

    init {

       loadNextItems()

    }

    fun loadNextItems(){

        viewModelScope.launch(Dispatchers.Main) {

            isLoading.value = true

            viewModelScope.launch(Dispatchers.IO) {

                var tempList = appRepository.getItems(startIndex, pageSize)

                viewModelScope.launch(Dispatchers.Main) {

                    itemsList.update{ currentList ->
                           val newList = arrayListOf<String>()
                           newList.addAll(currentList)
                           newList.addAll(tempList)
                           newList
                    }

                    startIndex = startIndex + pageSize

                    isLoading.value = false

                }

            }

        }
    }



}