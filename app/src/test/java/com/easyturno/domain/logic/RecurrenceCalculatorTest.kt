package com.easyturno.domain.logic

import com.easyturno.data.database.entities.Turno
import com.easyturno.data.models.RecurrenceType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecurrenceCalculatorTest {

    private lateinit var recurrenceCalculator: RecurrenceCalculator

    @Before
    fun setUp() {
        recurrenceCalculator = RecurrenceCalculator()
    }

    @Test
    fun `calcolaRicorrenze per DAILY genera un turno al giorno`() {
        val turnoTemplate = Turno(
            id = 1,
            titolo = "Test Mattina",
            dataInizio = LocalDateTime(2025, 8, 1, 8, 0),
            dataFine = LocalDateTime(2025, 8, 1, 14, 0),
            ricorrenza = RecurrenceType.DAILY,
            parametriRicorrenza = null
        )

        val rangeInizio = LocalDate(2025, 8, 1)
        val rangeFine = LocalDate(2025, 8, 3)

        val result = recurrenceCalculator.calcolaRicorrenze(turnoTemplate, rangeInizio, rangeFine)

        assertEquals(3, result.size)
        assertEquals(LocalDate(2025, 8, 1), result[0].dataInizio.date)
        assertEquals(LocalDate(2025, 8, 2), result[1].dataInizio.date)
        assertEquals(LocalDate(2025, 8, 3), result[2].dataInizio.date)
    }

    @Test
    fun `calcolaRicorrenze per WEEKLY genera un turno a settimana`() {
        val turnoTemplate = Turno(
            id = 1,
            titolo = "Test Settimanale",
            dataInizio = LocalDateTime(2025, 8, 4, 9, 0), // Un lunedì
            dataFine = LocalDateTime(2025, 8, 4, 17, 0),
            ricorrenza = RecurrenceType.WEEKLY,
            parametriRicorrenza = null
        )

        val rangeInizio = LocalDate(2025, 8, 1)
        val rangeFine = LocalDate(2025, 8, 20)

        val result = recurrenceCalculator.calcolaRicorrenze(turnoTemplate, rangeInizio, rangeFine)

        assertEquals(3, result.size)
        assertEquals(LocalDate(2025, 8, 4), result[0].dataInizio.date) // Lunedì
        assertEquals(LocalDate(2025, 8, 11), result[1].dataInizio.date) // Lunedì successivo
        assertEquals(LocalDate(2025, 8, 18), result[2].dataInizio.date) // Lunedì dopo ancora
    }

    @Test
    fun `calcolaRicorrenze per EVERY_N_DAYS con N=3 genera un turno ogni 3 giorni`() {
        val turnoTemplate = Turno(
            id = 1,
            titolo = "Ogni 3 giorni",
            dataInizio = LocalDateTime(2025, 8, 1, 10, 0),
            dataFine = LocalDateTime(2025, 8, 1, 11, 0),
            ricorrenza = RecurrenceType.EVERY_N_DAYS,
            parametriRicorrenza = "{\"every_n_days\":3}"
        )

        val rangeInizio = LocalDate(2025, 8, 1)
        val rangeFine = LocalDate(2025, 8, 10)

        val result = recurrenceCalculator.calcolaRicorrenze(turnoTemplate, rangeInizio, rangeFine)

        assertEquals(4, result.size)
        assertEquals(LocalDate(2025, 8, 1), result[0].dataInizio.date)
        assertEquals(LocalDate(2025, 8, 4), result[1].dataInizio.date)
        assertEquals(LocalDate(2025, 8, 7), result[2].dataInizio.date)
        assertEquals(LocalDate(2025, 8, 10), result[3].dataInizio.date)
    }

    @Test
    fun `calcolaRicorrenze per NONE genera un solo turno se nel range`() {
        val turnoTemplate = Turno(
            id = 1,
            titolo = "Turno Singolo",
            dataInizio = LocalDateTime(2025, 8, 15, 22, 0),
            dataFine = LocalDateTime(2025, 8, 16, 6, 0),
            ricorrenza = RecurrenceType.NONE,
            parametriRicorrenza = null
        )

        val rangeInizio = LocalDate(2025, 8, 1)
        val rangeFine = LocalDate(2025, 8, 31)

        val result = recurrenceCalculator.calcolaRicorrenze(turnoTemplate, rangeInizio, rangeFine)

        assertEquals(1, result.size)
        assertEquals(turnoTemplate, result[0])
    }
}
