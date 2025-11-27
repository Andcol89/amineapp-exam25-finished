package com.example.animeapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.animeapp.screens.animedetails.AnimeDetailsScreen
import com.example.animeapp.screens.animedetails.AnimeDetailsViewModel
import com.example.animeapp.screens.animesearch.SearchScreen
import com.example.animeapp.screens.animesearch.SearchViewModel
import com.example.animeapp.screens.createanime.CreateScreen
import com.example.animeapp.screens.createanime.CreateViewModel
import com.example.animeapp.screens.createanime.CreateCharacterScreen
import com.example.animeapp.screens.exstrastuff.ExstraScreen
import com.example.animeapp.screens.home.HomeScreen
import com.example.animeapp.screens.home.HomeScreenViewModel

@Composable
fun AppNavigation(
    searchViewModel: SearchViewModel,
    animeDetailsViewModel: AnimeDetailsViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    createViewModel: CreateViewModel
) {
    // NavController-objektet er det som utfører navigeringen og husker på hvilken screen brukeren er
    val navController = rememberNavController()

    // rememberSaveable tallverdi for å holde styr på hvilken skjerm man er på
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    var isDarkBackground by rememberSaveable {
        mutableStateOf(true)
    }

    val navigationTheme = NavigationBarItemDefaults.colors(
        indicatorColor = Color(0xFF423155),
        selectedIconColor = Color(0xFFD0D0F6),
        selectedTextColor = Color(0xFF9C9CE5),
        unselectedIconColor = Color(0xFF8663B6),
        unselectedTextColor = Color(0xFFF9D6FA)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF191829)
            ) {
                NavigationBarItem(
                    selected = selectedItemIndex == 1,
                    onClick = {
                        selectedItemIndex = 1
                        navController.navigate(NavRoutes.Home)
                    },
                    icon = {
                        if (selectedItemIndex == 1) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Home") },
                    colors = navigationTheme
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 2,
                    onClick = {
                        selectedItemIndex = 2
                        navController.navigate(NavRoutes.Search)
                    },
                    icon = {
                        if (selectedItemIndex == 2) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Anime Search") },
                    colors = navigationTheme
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 3,
                    onClick = {
                        selectedItemIndex = 3
                        navController.navigate(NavRoutes.Create)
                    },
                    icon = {
                        if (selectedItemIndex == 3) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Create") },
                    colors = navigationTheme
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 4,
                    onClick = {
                        selectedItemIndex = 4
                        navController.navigate(NavRoutes.Exstra)
                    },
                    icon = {
                        if (selectedItemIndex == 4) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Favorites") },
                    colors = navigationTheme
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkBackground) Color(0xFF120820) else Color(0xFFF5F1FF))
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavRoutes.Home
            ) {

                composable<NavRoutes.Home> {
                    HomeScreen(
                        isDarkBackground = isDarkBackground,
                        onAnimeClick = { id ->
                            navController.navigate(NavRoutes.AnimeDetails(id))
                        }
                    )
                }

                composable<NavRoutes.Search> {
                    SearchScreen(
                        searchViewModel,
                        navController
                    )
                }

                composable<NavRoutes.Create> {
                    CreateScreen(
                        navController = navController,
                        vm = createViewModel
                    )
                }

                composable<NavRoutes.CreateCharacter> { navEntry ->
                    val args = navEntry.toRoute<NavRoutes.CreateCharacter>()
                    CreateCharacterScreen(
                        navController = navController,
                        vm = createViewModel,
                        animeTitle = args.title,
                        animeDescription = args.description
                    )
                }

                composable<NavRoutes.AnimeDetails> { navEntry ->
                    val args = navEntry.toRoute<NavRoutes.AnimeDetails>()
                    AnimeDetailsScreen(
                        animeId = args.id,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable<NavRoutes.Exstra> {
                    ExstraScreen(
                        isDarkBackground = isDarkBackground,
                        changeBackground = {
                            isDarkBackground = !isDarkBackground
                        },
                        onAnimeClick = { id ->
                            navController.navigate(NavRoutes.AnimeDetails(id))
                        }
                    )
                }
            }
        }
    }
}
