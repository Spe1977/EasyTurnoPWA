package com.easyturno.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.easyturno.ui.screens.CalendarScreen
import com.easyturno.ui.screens.TurnoFormScreen
import com.easyturno.ui.screens.TurnoListScreen

/**
 * Header: Definisce le rotte di navigazione e il NavHost per l'applicazione.
 *
 * Centralizzare la logica di navigazione qui rende il codice più pulito e gestibile.
 */

/**
 * Sealed class per definire le rotte in modo type-safe.
 */
sealed class Screen(val route: String) {
    object TurnoList : Screen("turno_list")
    object TurnoForm : Screen("turno_form/{turnoId}") {
        fun createRoute(turnoId: Long) = "turno_form/$turnoId"
    }
    object Calendar : Screen("calendar")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.TurnoList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.TurnoList.route) {
            TurnoListScreen(navController = navController)
        }

        composable(
            route = Screen.TurnoForm.route,
            arguments = listOf(navArgument("turnoId") {
                type = NavType.LongType
                defaultValue = 0L // Default per la creazione di un nuovo turno
            })
        ) { backStackEntry ->
            val turnoId = backStackEntry.arguments?.getLong("turnoId")
            TurnoFormScreen(
                navController = navController,
                turnoId = turnoId
            )
        }

        composable(Screen.Calendar.route) {
            CalendarScreen(navController = navController)
        }
    }
}
