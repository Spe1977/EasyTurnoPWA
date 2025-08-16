package com.easyturno.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.easyturno.data.database.entities.Turno
import com.easyturno.ui.viewmodels.TurnoViewModel
import com.easyturno.utils.DateTimeUtils

/**
 * Header: Schermata principale che visualizza la lista dei turni.
 *
 * Questa schermata mostra tutti i turni futuri in una lista scorrevole.
 * Utilizza un ViewModel per recuperare i dati e un FloatingActionButton
 * per permettere all'utente di aggiungere nuovi turni.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnoListScreen(
    navController: NavController,
    viewModel: TurnoViewModel = hiltViewModel()
) {
    // Colleziona lo stato dal ViewModel in modo lifecycle-aware
    val turniState by viewModel.tuttiITurni.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("I Miei Turni") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("turno_form/0")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Aggiungi Turno")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (turniState.isEmpty()) {
                Text(
                    text = "Nessun turno programmato.\nPremi '+' per aggiungerne uno.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(turniState) { turno ->
                        TurnoItem(turno = turno)
                    }
                }
            }
        }
    }
}

/**
 * Composable che rappresenta un singolo elemento nella lista dei turni.
 */
@Composable
fun TurnoItem(turno: Turno) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = turno.titolo, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Nota: ${turno.note ?: "Nessuna nota"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = DateTimeUtils.formatDate(turno.dataInizio.date) ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${DateTimeUtils.formatTime(turno.dataInizio)} - ${DateTimeUtils.formatTime(turno.dataFine)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
