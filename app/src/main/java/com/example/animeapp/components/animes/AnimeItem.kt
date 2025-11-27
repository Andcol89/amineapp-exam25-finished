package com.example.animeapp.components.animes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.animeapp.data.api.Anime


@Composable
fun AnimeItem(
    anime: Anime,
    onClick: (Int)-> Unit) {

    Column(
        modifier = Modifier                                 //style
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(Color(76, 61, 105, 111)),

        horizontalAlignment = Alignment.CenterHorizontally) //<- midtstiller innhold

    {
        Text(modifier = Modifier
            .padding(top = 14.dp),
            text = anime.title,            //innhold
            color = Color.White,            //style
            fontWeight = FontWeight.Bold)

        Text(text = "ID: ${anime.id}",      //innhold
            color = Color.White,            //style
            fontWeight = FontWeight.Bold)

        Image(
            painter = rememberAsyncImagePainter(anime.images.jpg.image),
            contentDescription = anime.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) //pga rememberAsyncImagePainter må vi angi størrelse ved siden av .fillMaxWidth()
                .padding(14.dp)
        )
        Button(modifier = Modifier
            .padding(bottom = 14.dp),
            onClick = {onClick(anime.id)}
        ) {
            Text("view details")
        }
    }
}
