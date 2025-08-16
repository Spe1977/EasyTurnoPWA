package com.easyturno.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.easyturno.data.models.RecurrenceType
import com.easyturno.ui.viewmodels.TurnoViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Header: Schermata per la creazione e la modifica di un turno.
 *
 * Questa schermata contiene un form per inserire tutti i dettagli di un turno,
 * inclusa la gestione della ricorrenza. Gestisce lo stato dei campi di input
 * e interagisce con il ViewModel per salvare i dati.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnoFormScreen(
    navController: NavController,
    turnoId: Long?, // Nullable per distinguere creazione da modifica
    viewModel: TurnoViewModel = hiltViewModel()
) {
    // TODO: Caricare il turno dal viewModel se turnoId non è nullo

    // Stati per i campi del form
    var titolo by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var ricorrenza by remember { mutableStateOf(RecurrenceType.NONE) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (turnoId == 0L) "Nuovo Turno" else "Modifica Turno") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Indietro")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // TODO: Logica di salvataggio
                        // val nuovoTurno = Turno(...)
                        // viewModel.insertTurno(nuovoTurno)
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "Salva")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Titolo
            OutlinedTextField(
                value = titolo,
                onValueChange = { titolo = it },
                label = { Text("Titolo del turno") },
                modifier = Modifier.fillMaxWidth()
            )

            // TODO: Aggiungere Date/Time Pickers per dataInizio e dataFine

            // Ricorrenza
            RicorrenzaSelector(
                selected = ricorrenza,
                onSelected = { ricorrenza = it }
            )

            // Note
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note (opzionale)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RicorrenzaSelector(
    selected: RecurrenceType,
    onSelected: (RecurrenceType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Ricorrenza") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            RecurrenceType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
