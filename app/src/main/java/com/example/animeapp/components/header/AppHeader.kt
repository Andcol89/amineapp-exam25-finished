package com.example.animeapp.components.header



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animeapp.R

@Composable
fun AppHeader(title: String){
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .background(Color(55, 43, 70, 255))
    ) {
        Text(title,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0, 9, 21, 92))
                .padding(vertical = 12.dp),
            color=Color.White,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )

    }
}

@Composable
fun AppMiniHeader(title: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp) // valgfritt
    ) {
        Image(
            painter = painterResource(id = R.drawable.textbackground), // velg ditt bilde
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()   // fyll hele Box-en
        )
        Text(
            title,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(219, 186, 246, 27)) //gjennomsiktig slør
                .padding(vertical = 12.dp),
            color = Color.White,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )

    }
}