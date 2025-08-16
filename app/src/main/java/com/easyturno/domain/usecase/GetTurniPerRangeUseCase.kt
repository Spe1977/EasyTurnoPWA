package com.easyturno.domain.usecase

import com.easyturno.data.database.entities.Turno
import com.easyturno.domain.logic.RecurrenceCalculator
import com.easyturno.domain.repository.TurnoRepository
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate
import javax.inject.Inject

/**
 * Header: Use Case per ottenere tutti i turni, incluse le ricorrenze, per un dato intervallo.
 *
 * Questo Use Case orchestra il recupero dei turni base dal repository e la
 * generazione delle loro occorrenze tramite il RecurrenceCalculator.
 * Fornisce un singolo punto di accesso per la UI (tramite ViewModel) per ottenere
 * la lista completa e processata dei turni da visualizzare.
 */
class GetTurniPerRangeUseCase @Inject constructor(
    private val turnoRepository: TurnoRepository,
    private val recurrenceCalculator: RecurrenceCalculator
) {

    /**
     * Rende la classe invocabile come una funzione.
     * Es: `getTurniPerRangeUseCase(dataInizio, dataFine)`
     *
     * @param rangeInizio La data di inizio dell'intervallo.
     * @param rangeFine La data di fine dell'intervallo.
     * @return Una lista di tutti i turni e le loro occorrenze calcolate nell'intervallo,
     *         ordinata per data di inizio.
     */
    suspend operator fun invoke(rangeInizio: LocalDate, rangeFine: LocalDate): List<Turno> {
        // Raccoglie tutti i turni "template" dal database.
        // .first() prende il primo (e unico) valore emesso dal Flow.
        val tuttiITurniTemplate = turnoRepository.getAllTurni().first()

        val turniDaVisualizzare = mutableListOf<Turno>()

        tuttiITurniTemplate.forEach { turnoTemplate ->
            // Per ogni turno, calcola le sue occorrenze nell'intervallo di date richiesto.
            val occorrenze = recurrenceCalculator.calcolaRicorrenze(
                turno = turnoTemplate,
                rangeInizio = rangeInizio,
                rangeFine = rangeFine
            )
            turniDaVisualizzare.addAll(occorrenze)
        }

        // Ordina la lista finale per data di inizio prima di restituirla.
        return turniDaVisualizzare.sortedBy { it.dataInizio }
    }
}
