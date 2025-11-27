package com.example.animeapp.screens.exstrastuff

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.animeapp.data.local.exstra.FavoriteAnimeEntity
import com.example.animeapp.data.local.exstra.FavoritesState

@Composable
fun FavoritesSection(
    textColor: Color,
    onAnimeClick: (Int) -> Unit

) {
    val favorites = FavoritesState.favorites

    Spacer(Modifier.height(24.dp))

    if (favorites.isEmpty()) {
        Text(
            text = "You have no favorites yet 💔",
            color = textColor,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold
        )
    } else {
        Text(
            text = "your favorites ✨",
            color = textColor,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favorites) { fav ->
                FavoriteAnimeRow(
                    fav = fav,
                    textColor = textColor,
                    onClick = { onAnimeClick(fav.id)}
                )
            }
        }
    }
}


@Composable
fun FavoriteAnimeRow(
    fav: FavoriteAnimeEntity,
    textColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(fav.imageUrl),
            contentDescription = fav.title,
            modifier = Modifier
                .width(220.dp)
                .height(340.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = fav.title,
            color = textColor
        )
        Text(
            text = "ID: ${fav.id}",
            color = textColor
        )

        Spacer(Modifier.height(4.dp))

        Button(onClick = onClick) {
            Text("view details")
        }
    }
}
