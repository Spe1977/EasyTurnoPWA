package com.easyturno.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.easyturno.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

/**
 * Header: Test di UI per la TurnoListScreen.
 *
 * Questi test verificano che la UI di TurnoListScreen si comporti come atteso.
 * Utilizzano Hilt per l'iniezione delle dipendenze nel contesto del test.
 */
@HiltAndroidTest
class TurnoListScreenTest {

    // Regola di Hilt per gestire le dipendenze nei test strumentati.
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // Regola per interagire con i Composable e l'Activity.
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun statoVuoto_mostraMessaggioCorretto() {
        // Poiché il database è vuoto all'inizio del test, ci aspettiamo
        // di vedere il messaggio per lo stato vuoto.

        // Arrange: Definisci la stringa che ci aspettiamo di trovare
        val expectedMessage = "Nessun turno programmato."

        // Act & Assert: Cerca un nodo con un testo che contiene il nostro messaggio
        // e verifica che sia visualizzato.
        composeTestRule.onNodeWithText(expectedMessage, substring = true).assertIsDisplayed()
    }

    // Un test più avanzato potrebbe scambiare il modulo Hilt con uno di test
    // per fornire un TurnoRepository fittizio che emette una lista di turni,
    // e poi verificare che la LazyColumn mostri quegli elementi.
}
