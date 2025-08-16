package com.easyturno.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.easyturno.ui.viewmodels.TurnoViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Header: Schermata per la visualizzazione del calendario mensile.
 *
 * Mostra un calendario del mese corrente e permette di navigare tra i mesi.
 * Evidenzia i giorni in cui sono presenti dei turni.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: TurnoViewModel = hiltViewModel()
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    // TODO: Chiamare il viewModel per caricare i turni del mese corrente (currentMonth)

    Scaffold(
        topBar = {
            CalendarTopAppBar(
                currentMonth = currentMonth,
                onPrevMonth = { currentMonth = currentMonth.minusMonths(1) },
                onNextMonth = { currentMonth = currentMonth.plusMonths(1) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Placeholder per il calendario
            Text(
                "Calendario per ${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.ITALIAN)} ${currentMonth.year}",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
            // Qui andrà implementata la griglia del calendario (es. con LazyVerticalGrid)
        }
    }
}

@OptIn(ExperimentalMaterial3Dsl::class)
@Composable
private fun CalendarTopAppBar(
    currentMonth: YearMonth,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.ITALIAN).replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onPrevMonth) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Mese Precedente")
            }
        },
        actions = {
            IconButton(onClick = onNextMonth) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Mese Successivo")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}
