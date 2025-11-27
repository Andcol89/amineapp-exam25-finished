package com.example.animeapp.screens.animedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.animeapp.components.header.AppHeader
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.animeapp.data.local.exstra.FavoritesState
import kotlinx.coroutines.launch

@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    onBack: () -> Unit,
    animeVm: AnimeDetailsViewModel = viewModel()
){

    val animeDetails by animeVm.anime.collectAsState()

    val scrollState = rememberScrollState() //scroll

    var isFavorite by remember{ mutableStateOf(false) } // for favorittknappen
    var hasClickedFavoriteButton by remember {mutableStateOf(false)}

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    LaunchedEffect(animeId) {
        animeVm.load(animeId)

        FavoritesState.loadFromDb(context)

        isFavorite = FavoritesState.isFavorite(animeId)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .verticalScroll(scrollState) //scroll

    ) {
        AppHeader("Anime info")

        // Øverst på siden. Knapp + navn på anime + id + favorittknapp
        Row(modifier = Modifier
            .padding(top = 12.dp)

            .fillMaxWidth()
            .background(Color(0xFFE0E0E0))

        ) {

            Button(onClick = onBack,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp)
                    .padding(bottom = 25.dp)

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
                // Her har vi hatt en utfordring med at teksten
                // kan være for lang og presser favorittknappen ut av synet.
            Text(text = "${animeDetails?.title}" + " (ID: ${animeDetails?.id})",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,

                modifier = Modifier
                    .padding(24.dp)
                    .padding(top = 5.dp)
                    .weight(1f) //med denne så legger teksten seg nedover og knappen blir ikke dyttet ut.
            )

            //favorittknappen -----------------------


            IconButton(onClick =  {
                isFavorite = !isFavorite
                hasClickedFavoriteButton = true


                //Lagre og fjerne
                val details = animeDetails
                if (details != null){
                    scope.launch {
                        FavoritesState.toggleFavorite(context, details)
                    }
                  }
                },

                modifier = Modifier
                    .padding(13.dp)
                    .padding(horizontal = 10.dp)
                    .size(55.dp) //dette er ikke størrelsen på stjernen men hvor stort det trykkbare området er.

            ) {
                Icon(
                    imageVector =
                        if (isFavorite)
                     {Icons.Filled.Star}
                        else
                      { Icons.Outlined.Star},
                    contentDescription = "Favorites button",
                    modifier = Modifier
                        .size(50.dp), //dette er størrelsen på stjerne ikonet.

                    tint = if (isFavorite) {Color(0xFFFFD54F)} else {Color.Gray}

                )




            }


        } // row end.

        // for brukerinformasjon tekst
        Row (modifier = Modifier
            .fillMaxWidth()) {

            if (hasClickedFavoriteButton) { //vil bare vise meldingen etter bruker har klikket.
                Text(

                    text =
                        if (isFavorite) {
                            "Added to favorite"
                        } else {
                            "Removed from favorites"
                        },

                    color =
                        if (isFavorite) {
                            Color(0xFF2E7D32)
                        } else {
                            Color(0xFFC62828)
                        },

                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(14.dp)
                )
            }
        }
//Favorittknapp kode ferdig --------




                                //Viser tilbakemelding til bruker ut i fra hva som har skjedd
        if (animeVm.isLoading){
            Text("Loading..")
        } else if (animeVm.error != null) {
            Text("error: ${animeVm.error}")
        } else if (animeDetails != null) {

            val details = animeDetails!! //  Vi sørger for at verdien ikke er null.
                                         //  vi kan bruke denne her fordi vi har allerde i "else if" sjekket at det ikke er null.
                                         //  Kan kalle varaiblen for anime egentlig men bestemte oss for details.
            // - Design
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.Start
            ) {



                Spacer(Modifier.height(24.dp))


                //Her så vi at ofte ble informasjonen satt til "null".
                // Da kan du bruke ?: som gir oss muligheten til å vise noe annet en null.

                Text(text = "${details.year ?: "Unknown year"}  -  ${details.ageRating ?: "(No age rating)"}  -  ${details.duration ?: "lenght not specified"}",
                    fontSize = 14.sp)

                Row(
                        verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(details.images.jpg.image), // må bruke rememberAsyncImagePainter() og ikke painterResource
                        contentDescription = details.title,                                        // Her får vi hjelp av coil.
                        modifier = Modifier
                            .width(150.dp)
                            .height(300.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Column (modifier = Modifier
                        .padding(top = 52.dp)
                        .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ){
                                        // Her må vi bruke ?: igjen.
                        Text(text = "Episodes: ${details.episodes ?: "Unknown"}")
                        Text(text = "Score: ${details.score ?: "0.0" }")
                        Text(text = "Ranking: ${details.rank ?: "0"}")
                        Text(text = "Status: ${details.status ?: "Unknown"}")

                    }

                }// End Row

                // For å få Synopsis mer lesbar for bruker så det ikke blir en vegg av tekst.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(Color(0xFFE0E0E0))
                        .padding(15.dp)
                ) {
                    Text(
                        text = "Synopsis",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(9.dp))

                    Text(text = "Synopsis: ${details.synopsis ?: "no synopsis"}",
                        fontSize = 9.sp,
                        lineHeight = 20.sp)

                }



            } // end main Column
        } else {
            Text("Fant dessverre ikke noe informasjon om denne serien")
        }

    }

}