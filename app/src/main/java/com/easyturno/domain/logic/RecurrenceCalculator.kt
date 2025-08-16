package com.easyturno.domain.logic

import com.easyturno.data.database.entities.Turno
import com.easyturno.data.models.RecurrenceType
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject

/**
 * Header: Calcolatore per la logica delle ricorrenze dei turni.
 *
 * Questa classe contiene la logica di business per generare le occorrenze
 * di un turno basandosi sulla sua regola di ricorrenza.
 */
class RecurrenceCalculator @Inject constructor() {

    /**
     * Genera una lista di turni concreti basati su un turno "template" con ricorrenza.
     *
     * @param turno Il turno originale che definisce la ricorrenza.
     * @param rangeInizio La data di inizio dell'intervallo per cui generare le occorrenze.
     * @param rangeFine La data di fine dell'intervallo.
     * @return Una lista di Turno, dove ogni elemento è un'occorrenza calcolata.
     */
    fun calcolaRicorrenze(
        turno: Turno,
        rangeInizio: LocalDate,
        rangeFine: LocalDate
    ): List<Turno> {
        if (turno.ricorrenza == RecurrenceType.NONE) {
            return if (turno.dataInizio.date in rangeInizio..rangeFine) listOf(turno) else emptyList()
        }

        val occorrenze = mutableListOf<Turno>()
        var dataCorrente = turno.dataInizio

        while (dataCorrente.date <= rangeFine) {
            if (dataCorrente.date >= rangeInizio) {
                // Crea una nuova istanza del turno per questa occorrenza.
                // L'ID 0 indica che è un'istanza generata, non quella salvata nel DB.
                // Potremmo voler usare un ID diverso o un flag per distinguerli.
                occorrenze.add(
                    turno.copy(
                        id = 0, // ID fittizio per le istanze generate
                        dataInizio = dataCorrente,
                        dataFine = turno.dataFine.plus(DatePeriod(days = (dataCorrente.date.toEpochDays() - turno.dataInizio.date.toEpochDays()))),
                        ricorrenza = RecurrenceType.NONE, // Le istanze non sono ricorrenti
                        parametriRicorrenza = null
                    )
                )
            }

            // Calcola la data successiva in base al tipo di ricorrenza
            dataCorrente = when (turno.ricorrenza) {
                RecurrenceType.DAILY -> dataCorrente.plus(DatePeriod(days = 1))
                RecurrenceType.WEEKLY -> dataCorrente.plus(DatePeriod(weeks = 1))
                RecurrenceType.MONTHLY -> dataCorrente.plus(DatePeriod(months = 1))
                RecurrenceType.YEARLY -> dataCorrente.plus(DatePeriod(years = 1))
                RecurrenceType.EVERY_N_DAYS -> {
                    val n = parseN(turno.parametriRicorrenza, "every_n_days")
                    dataCorrente.plus(DatePeriod(days = n))
                }
                RecurrenceType.EVERY_N_WEEKS -> {
                    val n = parseN(turno.parametriRicorrenza, "every_n_weeks")
                    dataCorrente.plus(DatePeriod(weeks = n))
                }
                RecurrenceType.QUARTERLY -> dataCorrente.plus(DatePeriod(months = 3))
                RecurrenceType.BIANNUAL -> dataCorrente.plus(DatePeriod(months = 6))
                RecurrenceType.NONE -> break // Non dovrebbe succedere, ma per sicurezza
            }
        }
        return occorrenze
    }

    /**
     * Funzione helper per parsare il parametro numerico da una stringa JSON.
     * Semplificato per ora, una soluzione robusta userebbe kotlinx.serialization.
     */
    private fun parseN(json: String?, key: String): Int {
        // Implementazione semplificata. In produzione, usare una libreria JSON.
        return json?.substringAfter("\"$key\":")?.substringBefore("}")?.trim()?.toIntOrNull() ?: 1
    }
}
