package com.example.animeapp.screens.createanime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.animeapp.components.header.AppMiniHeader
import com.example.animeapp.data.local.AnimeIdeaEntity
import com.example.animeapp.navigation.NavRoutes
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun CreateScreen(
    navController: NavController,
    vm: CreateViewModel
) {
    // Input hentes fra ViewModel (tittel og beskrivelse som overlever navigasjon)
    val title by vm.title.collectAsState()
    val description by vm.description.collectAsState()

    // Om lagrede animes/karakter skal vises
    val showSaved by vm.showSaved.collectAsState()

    // Feilmelding til bruker - lokal state
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Alle lagrede ideer fra Room
    val ideas: List<AnimeIdeaEntity> by vm.ideas.collectAsState()

    // Siste lagrede karakter (fra CreateCharacterScreen)
    val lastCharacter by vm.lastCharacter.collectAsState()

    // Valgt idé for redigering
    val selectedIdea by vm.selectedIdea.collectAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            AppMiniHeader("Create Anime Idea")

            Spacer(Modifier.height(12.dp))

            // Tekstfelt for tittel på anime-serien
            OutlinedTextField(
                value = title,
                onValueChange = { vm.setTitle(it) },
                label = { Text("Anime title") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Enter the name of the anime series.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(8.dp))

            // Tekstfelt for beskrivelse av anime-serien
            OutlinedTextField(
                value = description,
                onValueChange = { vm.setDescription(it) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Write a short description of what the series is about.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "When you are happy with your idea, you can create characters for this anime.",
                style = MaterialTheme.typography.bodySmall
            )

            // Info om redigering når en idé er valgt
            selectedIdea?.let {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "You are editing this anime idea. Change the text above and press \"Update anime idea\" to save.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(16.dp))

            // Gå til CreateCharacterScreen med nåværende tittel og beskrivelse
            Button(
                onClick = {
                    navController.navigate(
                        NavRoutes.CreateCharacter(
                            title = title,
                            description = description
                        )
                    )
                },
                enabled = title.isNotBlank() || description.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text("Create character")
            }

            Spacer(Modifier.height(40.dp))

            Text(
                "Manage your saved anime ideas here:",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(4.dp))

            // Ekstra forklaring til brukeren om redigering
            Text(
                text = "Tap a saved anime idea below (it will be highlighted in blue) to select it. Then edit the text above and press \"Update anime idea\" to save your changes.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


            Spacer(Modifier.height(12.dp))

            // Knapper for å vise, lagre og oppdatere ideer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    // Knapp for å vise lagrede ideer og eventuelt lagre en ny
                    Button(
                        onClick = {
                            errorMessage = null
                            vm.setShowSaved(true)

                            val hasInput = title.isNotBlank() || description.isNotBlank()

                            // Hvis det er tekst i ett av feltene prøver vi å lagre en ny idé
                            if (hasInput) {
                                if (ideas.size >= 3) {
                                    errorMessage = "You can save a maximum of 3 anime ideas."
                                } else {
                                    vm.addIdea(title, description)
                                    vm.setTitle("")
                                    vm.setDescription("")
                                    vm.clearSelectedIdea()
                                }
                            }
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text("View saved anime ideas")
                    }

                    Spacer(Modifier.height(8.dp))

                    // Knapp for å oppdatere en eksisterende idé (må være valgt)
                    Button(
                        onClick = {
                            errorMessage = null

                            val currentSelection = selectedIdea
                            val hasInput = title.isNotBlank() || description.isNotBlank()

                            when {
                                // Ingen ide valgt
                                currentSelection == null -> {
                                    errorMessage = "Please tap on a saved anime idea below to select it before updating."
                                }
                                // Ingen ny tekst skrevet inn
                                !hasInput -> {
                                    errorMessage = "Please change the title and/or description before updating."
                                }
                                else -> {
                                    // Bruk ny tekst hvis feltet ikke er tomt, ellers behold gammel verdi
                                    val newTitle =
                                        if (title.isNotBlank()) title else currentSelection.title
                                    val newDescription =
                                        if (description.isNotBlank()) description else currentSelection.description

                                    vm.updateIdea(
                                        id = currentSelection.id,
                                        title = newTitle,
                                        description = newDescription
                                    )

                                    vm.setTitle("")
                                    vm.setDescription("")
                                    vm.clearSelectedIdea()
                                    vm.setShowSaved(true)
                                }
                            }
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text("Update anime idea")
                    }
                }
            }

            // Viser feilmelding til bruker hvis noe gikk galt
            errorMessage?.let { msg ->
                Spacer(Modifier.height(8.dp))
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            // Viser lagrede ideer og siste karakter
            if (showSaved) {
                Text(
                    "Saved anime ideas (${ideas.size}):",
                    style = MaterialTheme.typography.bodySmall
                )

                if (ideas.isEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "You have not saved any anime ideas yet.",
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Spacer(Modifier.height(6.dp))

                    // Viser alle lagrede anime-ideer i klikkbare bokser
                    ideas.forEach { idea ->
                        val isSelected = selectedIdea?.id == idea.id

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) Color(0xFFDDE8FF)  // markert idé
                                    else Color(0xFFF2F2F2)             // vanlig lys grå boks
                                )
                                .padding(12.dp)
                                .clickable {
                                    // Velg denne ideen for redigering og fyll feltene
                                    vm.selectIdea(idea)
                                    vm.setTitle(idea.title)
                                    vm.setDescription(idea.description)
                                    errorMessage = null
                                }
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Anime: ") }
                                    append(idea.title)
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (idea.description.isNotBlank()) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Description: ") }
                                        append(idea.description)
                                    },
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Tap this card to load and edit this idea.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                    }

                    // Viser siste lagrede karakter med anime-informasjon
                    lastCharacter?.let { c ->
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Last saved character:",
                            style = MaterialTheme.typography.bodySmall
                        )

                        val goodOrBad = if (c.isGood) "Good" else "Evil"
                        val magicText = if (c.hasMagic) "Has magic" else "No magic"
                        val mainText = if (c.isMain) "Main character" else "Character"

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE8E8FF))
                                .padding(6.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Anime: ") }
                                    append(c.animeTitle)
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (c.animeDescription.isNotBlank()) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Description: ") }
                                        append(c.animeDescription)
                                    },
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Name: ") }
                                    append(c.name)
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Relation: ") }
                                    append(c.relation)
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Traits: ") }
                                    append("$goodOrBad, $magicText, $mainText")
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(72.dp))
        }

        // Delete-knapp nederst for å slette siste lagrede anime-idé
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    val lastIdea = ideas.firstOrNull()
                    if (lastIdea != null) {
                        vm.deleteIdea(lastIdea)
                    }
                },
                enabled = ideas.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 16.dp)
                    .height(40.dp)
            ) {
                Text("Delete anime idea")
            }
        }
    }
}
