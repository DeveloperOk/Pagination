package com.enterprise.pagination

import kotlinx.coroutines.delay

class AppRepository {


    public suspend fun getItems(startIndex: Int, pageSize: Int): List<String>{

        delay(2000)

        var outputList = arrayListOf<String>()

        for(index in startIndex..startIndex+pageSize-1){

            outputList.add("Item: " + index)

        }

        return  outputList

    }


}