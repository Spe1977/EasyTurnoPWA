package com.easyturno.domain.repository

import com.easyturno.data.database.entities.Turno
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

/**
 * Header: Interfaccia per il Repository dei Turni.
 *
 * Questa interfaccia definisce il contratto per l'accesso ai dati dei turni,
 * astraendo la fonte dei dati (che in questo caso è Room, ma potrebbe essere un'API remota).
 * Fa parte del layer di dominio, esponendo solo le operazioni di business necessarie
 * ai ViewModel e ai UseCase.
 */
interface TurnoRepository {

    /**
     * Inserisce un nuovo turno.
     */
    suspend fun insertTurno(turno: Turno)

    /**
     * Aggiorna un turno esistente.
     */
    suspend fun updateTurno(turno: Turno)

    /**
     * Cancella un turno.
     */
    suspend fun deleteTurno(turno: Turno)

    /**
     * Recupera un turno specifico tramite ID.
     */
    suspend fun getTurnoById(id: Long): Turno?

    /**
     * Ottiene un Flow con tutti i turni, ordinati per data.
     */
    fun getAllTurni(): Flow<List<Turno>>

    /**
     * Recupera i turni in un intervallo di date.
     */
    suspend fun getTurniForDateRange(dataInizio: LocalDateTime, dataFine: LocalDateTime): List<Turno>
}
