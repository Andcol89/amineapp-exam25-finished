package com.example.animeapp.screens.createanime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.animeapp.components.header.AppMiniHeader
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

data class AnimeCharacter(
    val name: String,
    val isGood: Boolean,
    val hasMagic: Boolean,
    val isMain: Boolean,
    val relation: String,
    val animeTitle: String,
    val animeDescription: String
)

@Composable
fun CreateCharacterScreen(
    navController: NavController,
    vm: CreateViewModel,
    animeTitle: String,
    animeDescription: String
) {
    var charName by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var isGood by remember { mutableStateOf(true) }
    var hasMagic by remember { mutableStateOf(false) }
    var isMain by remember { mutableStateOf(false) }

    val savedCharacters = remember { mutableStateListOf<AnimeCharacter>() }
    val scrollState = rememberScrollState()

    // Feilmelding for karakter-lagring
    var charError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(12.dp)
            .background(Color.White)
    ) {
        AppMiniHeader("Create Character")

        OutlinedTextField(
            value = charName,
            onValueChange = { charName = it },
            label = { Text("Character name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = relation,
            onValueChange = { relation = it },
            label = { Text("Relation (rival, friend, parent, teacher, etc.)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Snill / Slem
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Checkbox(
                    checked = isGood,
                    onCheckedChange = { checked -> isGood = checked }
                )
                Text("Good")
            }
            Row {
                Checkbox(
                    checked = !isGood,
                    onCheckedChange = { checked -> isGood = !checked }
                )
                Text("Evil")
            }
        }

        // Har magi
        Row {
            Checkbox(
                checked = hasMagic,
                onCheckedChange = { checked -> hasMagic = checked }
            )
            Text("Has magical powers")
        }

        // Hovedkarakter
        Row {
            Checkbox(
                checked = isMain,
                onCheckedChange = { checked -> isMain = checked }
            )
            Text("Main character")
        }

        Spacer(Modifier.height(16.dp))

        // Lagre karakter – maks 3 lagrede
        Button(
            onClick = {
                charError = null

                if (charName.isBlank()) {
                    charError = "Please enter a character name."
                    return@Button
                }

                if (savedCharacters.size >= 3) {
                    charError = "You can save a maximum of 3 characters."
                    return@Button
                }

                val character = AnimeCharacter(
                    name = charName,
                    isGood = isGood,
                    hasMagic = hasMagic,
                    isMain = isMain,
                    relation = relation,
                    animeTitle = animeTitle,
                    animeDescription = animeDescription
                )

                // Vis på denne siden
                savedCharacters.add(character)

                // Lagre i ViewModel slik at CreateScreen kan vise siste karakter
                vm.saveCharacter(character)

                // Nullstill felter
                charName = ""
                relation = ""
                isGood = true
                hasMagic = false
                isMain = false
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text("Save character")
        }

        // Vis feilmelding hvis noe ikke går som det skal
        charError?.let { msg ->
            Spacer(Modifier.height(8.dp))
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(12.dp))

        if (savedCharacters.isNotEmpty()) {
            Text(
                "Saved characters (${savedCharacters.size}/3):",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(6.dp))

            savedCharacters.forEach { c ->
                val goodOrBad = if (c.isGood) "Good" else "Evil"
                val magicText = if (c.hasMagic) "Has magic" else "No magic"
                val mainText = if (c.isMain) "Main character" else "Not main Character"

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF2F2F2))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Venstre felt
                        Column(
                            modifier = Modifier.weight(1f)
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
                        }

                        // Høyre felt
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
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

                Spacer(Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text("Back to Create Anime")
        }
    }
}
