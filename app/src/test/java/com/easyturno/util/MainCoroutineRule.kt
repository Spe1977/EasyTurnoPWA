package com.easyturno.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Header: Regola JUnit per la gestione del Main dispatcher nei test delle coroutine.
 *
 * Questa regola sostituisce il dispatcher `Dispatchers.Main` con un dispatcher di test
 * prima dell'esecuzione di ogni test e lo ripristina al termine. Questo è fondamentale
 * per testare i ViewModel che lanciano coroutine nel `viewModelScope`.
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
