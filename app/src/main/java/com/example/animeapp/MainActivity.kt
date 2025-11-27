package com.example.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.animeapp.ui.theme.AnimeAppTheme
import com.example.animeapp.navigation.AppNavigation
import com.example.animeapp.screens.animedetails.AnimeDetailsViewModel
import com.example.animeapp.screens.animesearch.SearchViewModel
import com.example.animeapp.screens.createanime.CreateViewModel
import com.example.animeapp.screens.home.HomeScreenViewModel

class MainActivity : ComponentActivity() {

    private val _searchViewModel : SearchViewModel by viewModels()
    private val _animeDetailsViewModel : AnimeDetailsViewModel by viewModels()
    private val _homeScreenViewModel: HomeScreenViewModel by viewModels()
    private val _createViewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeAppTheme {
                AppNavigation(
                    _searchViewModel,
                    _animeDetailsViewModel,
                    _homeScreenViewModel,
                    _createViewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimeAppTheme {
        Greeting("Android")
    }
}