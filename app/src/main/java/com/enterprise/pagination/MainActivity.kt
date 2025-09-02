package com.enterprise.pagination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enterprise.pagination.ui.theme.PaginationTheme


class MainActivity : ComponentActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            PaginationTheme {

                PaginationApp(mainViewModel = mainViewModel)

            }
        }
    }

    @Composable
    private fun PaginationApp(mainViewModel: MainViewModel) {
        Scaffold(modifier = Modifier.systemBarsPadding().fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)){

                MainBody(mainViewModel = mainViewModel)

            }
        }
    }

    private @Composable
    fun MainBody(mainViewModel: MainViewModel) {

        val itemsList = mainViewModel.itemsList.collectAsStateWithLifecycle()
        val isLoading = mainViewModel.isLoading.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.fillMaxSize()){

            itemsIndexed(itemsList.value){ index, item ->

               if(index ==
                   (itemsList.value.size - 1 - mainViewModel.numberOfElementsFromTheEndOfList )
                   && !isLoading.value){

                    mainViewModel.loadNextItems()

               }

               RowItem(item = item)

            }


            item{
                if(isLoading.value){
                    Column(modifier = Modifier.fillMaxWidth().height(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                        CircularProgressIndicator(color = Color.Blue)
                    }
                }
            }

        }

    }

    private @Composable
    fun RowItem(item: String) {

        Text(text = item,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp).border(2.dp, Color.Green)
                .padding(10.dp),
            textAlign = TextAlign.Center)

    }


}

