package com.easyturno.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.easyturno.data.database.entities.Turno
import com.easyturno.data.models.RecurrenceType
import com.easyturno.domain.repository.TurnoRepository
import com.easyturno.util.MainCoroutineRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TurnoViewModelTest {

    // Sostituisce il Main dispatcher per i test.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Esegue ogni task di architettura in modo sincrono.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Mock del repository
    private lateinit var mockRepository: TurnoRepository
    private lateinit var viewModel: TurnoViewModel

    @Before
    fun setUp() {
        // Crea un mock del repository prima di ogni test
        mockRepository = mockk(relaxed = true)
        // Inizializza il ViewModel con il repository mockato
        viewModel = TurnoViewModel(mockRepository)
    }

    @Test
    fun `tuttiITurni espone correttamente i dati dal repository`() = runTest {
        // Arrange: Prepara i dati fittizi e il comportamento del mock
        val turniFittizi = listOf(
            Turno(1, "Turno 1", LocalDateTime(2025, 1, 1, 8, 0), LocalDateTime(2025, 1, 1, 16, 0), RecurrenceType.NONE, null),
            Turno(2, "Turno 2", LocalDateTime(2025, 1, 2, 8, 0), LocalDateTime(2025, 1, 2, 16, 0), RecurrenceType.NONE, null)
        )
        every { mockRepository.getAllTurni() } returns flowOf(turniFittizi)

        // Act: Inizializza il viewModel per triggerare la collection del flow
        viewModel = TurnoViewModel(mockRepository)
        val result = viewModel.tuttiITurni.first() // Prende il primo valore emesso

        // Assert: Verifica che i dati esposti dal ViewModel siano quelli attesi
        assertEquals(2, result.size)
        assertEquals("Turno 1", result[0].titolo)
    }

    @Test
    fun `insertTurno chiama il metodo insert del repository`() = runTest {
        // Arrange
        val nuovoTurno = Turno(3, "Turno Nuovo", LocalDateTime(2025, 1, 3, 8, 0), LocalDateTime(2025, 1, 3, 16, 0), RecurrenceType.NONE, null)

        // Act
        viewModel.insertTurno(nuovoTurno)

        // Assert: Verifica che il metodo del repository sia stato chiamato esattamente una volta con l'oggetto corretto.
        coVerify(exactly = 1) { mockRepository.insertTurno(nuovoTurno) }
    }
}
