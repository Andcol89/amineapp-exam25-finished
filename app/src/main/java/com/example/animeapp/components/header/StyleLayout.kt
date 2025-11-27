package com.example.animeapp.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.animeapp.R

@Composable
fun StyleLayoutDark(
    title: String,
    modifier: Modifier = Modifier,                  //for å endre styling i Column utenfra (endringer særpreg/ulike - screens)
    content: @Composable ColumnScope.() -> Unit = {} //for å legge til innhold under headeren (utenfra - screen)

){
    Box(modifier = modifier
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background3), //bakgrunnsbilde
            contentDescription = "background",                     //beskrivelse
            contentScale = ContentScale.Crop,                      //bilde form
            modifier = Modifier.matchParentSize()                  //fyller skjerm
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(12, 3, 3, 107)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader(title = title)
            content()
        }
    }
}

@Composable
fun StyleLayoutLight(
    title: String,
    modifier: Modifier = Modifier,                   //for å endre styling i Column utenfra (endringer særpreg/ulike - screens)
    content: @Composable ColumnScope.() -> Unit = {} //for å legge til innhold under headeren (utenfra - screen)

){
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background6), //bakgrunnsbilde
            contentDescription = "background",                     //beskrivelse
            contentScale = ContentScale.Crop,                      //bilde form
            modifier = Modifier.matchParentSize()                  //fyller skjerm
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(255, 255, 255, 34)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppMiniHeader(title = title)
            content()
        }
    }
}