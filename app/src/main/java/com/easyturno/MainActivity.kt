package com.easyturno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.easyturno.ui.navigation.AppNavHost
import com.easyturno.ui.theme.EasyTurnoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Header: Activity principale e punto di ingresso dell'UI dell'applicazione.
 *
 * Questa activity ospita il NavHost di Jetpack Compose, che gestisce la
 * navigazione tra le diverse schermate (Composable).
 *
 * L'annotazione @AndroidEntryPoint è fondamentale per abilitare l'iniezione
 * di dipendenze da parte di Hilt in questa Activity e nei Composable
 * che utilizzano ViewModel iniettati.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyTurnoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
