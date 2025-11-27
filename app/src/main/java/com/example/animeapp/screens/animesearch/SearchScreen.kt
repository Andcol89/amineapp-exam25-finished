package com.example.animeapp.screens.animesearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.animeapp.components.animes.AnimeItem
import com.example.animeapp.components.header.AppMiniHeader
import com.example.animeapp.navigation.NavRoutes

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    navController: NavController
){

    val visVerdi = searchViewModel.offentligVerdi.collectAsState()

    var idAndName by remember {
        mutableStateOf("")
    }

    // Har det blitt trykket på søk en gang.
    var buttonPushed by remember {mutableStateOf(false)}

    // hva det sto i søkerfelte når du søkte
    var rememberSearch by remember {mutableStateOf("")}



    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .background(Color.White)
    ) {
        AppMiniHeader("Anime Search")

        Spacer(Modifier.height(8.dp))

        TextField(
            value = idAndName,
            onValueChange = {idAndName = it},
            label = { Text("Search by ID or name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = {

                buttonPushed = true
                rememberSearch = idAndName

                if (idAndName.isNotBlank()) {
                    searchViewModel.search(idAndName)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("search")
        }
        Spacer(Modifier.height(8.dp))

        // Knappen har ikke blitt trykket på enda.
        if (!buttonPushed){
            Text("…search to show anime")
        } else {
            // Brukeren trykker på søk uten å skrive noe.
            if (rememberSearch.isBlank()){
                Text("You must enter an ID or a name")
            } else {

                //Når brukeren har skrevet inn noe og trykket på søk
                visVerdi.value?.let { anime ->
                    AnimeItem(
                        anime = anime,
                        onClick = {
                            navController.navigate(
                                NavRoutes.AnimeDetails(id= anime.id)
                            )
                        }
                    )
                } ?: Text("Couldn’t find any anime with that ID or that name")

            }
        }




    }
}