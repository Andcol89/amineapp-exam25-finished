package com.example.animeapp.screens.exstrastuff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.animeapp.components.header.StyleLayoutDark
import com.example.animeapp.components.header.StyleLayoutLight
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.animeapp.data.local.exstra.FavoritesState



@Composable
fun ExstraScreen(
    isDarkBackground: Boolean,
    changeBackground: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        FavoritesState.loadFromDb(context)
    }

    val layoutTitle = "Your favorites!"

    if (isDarkBackground) {
        StyleLayoutDark(
            title = layoutTitle,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Here you can change to light background",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                )
                Button(
                    onClick = changeBackground
                ) {
                    Text("Change to Light background")
                }

                Spacer(Modifier.height(10.dp))

                // Anime-Favoritter – hvit tekst på mørk bakgrunn
                FavoritesSection(
                    textColor = Color.White,
                    onAnimeClick = onAnimeClick
                )
            }
        }
    } else {
        StyleLayoutLight(
            title = layoutTitle,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Change back to dark background",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                )
                Button(onClick = changeBackground) {
                    Text("Change to dark background")
                }

                Spacer(Modifier.height(10.dp))

                // Anime-Favoritter – svart tekst på lys bakgrunn
                FavoritesSection(
                    textColor = Color.Black,
                    onAnimeClick = onAnimeClick
                )
            }
        }
    }
}


