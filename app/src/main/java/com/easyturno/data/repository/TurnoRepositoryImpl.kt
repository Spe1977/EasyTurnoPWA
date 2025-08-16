package com.easyturno.data.repository

import com.easyturno.data.database.dao.TurnoDao
import com.easyturno.data.database.entities.Turno
import com.easyturno.domain.repository.TurnoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

/**
 * Header: Implementazione concreta del TurnoRepository.
 *
 * Questa classe implementa l'interfaccia TurnoRepository definita nel layer di dominio.
 * Si occupa di orchestrare le operazioni di accesso ai dati, in questo caso delegando
 * le chiamate al DAO di Room.
 *
 * L'annotazione @Inject nel costruttore indica a Hilt come creare un'istanza
 * di questa classe, provvedendo automaticamente l'istanza di TurnoDao.
 *
 * @param turnoDao Il DAO per l'accesso alla tabella dei turni.
 */
class TurnoRepositoryImpl @Inject constructor(
    private val turnoDao: TurnoDao
) : TurnoRepository {

    override suspend fun insertTurno(turno: Turno) {
        turnoDao.insertTurno(turno)
    }

    override suspend fun updateTurno(turno: Turno) {
        turnoDao.updateTurno(turno)
    }

    override suspend fun deleteTurno(turno: Turno) {
        turnoDao.deleteTurno(turno)
    }

    override suspend fun getTurnoById(id: Long): Turno? {
        return turnoDao.getTurnoById(id)
    }

    override fun getAllTurni(): Flow<List<Turno>> {
        return turnoDao.getAllTurni()
    }

    override suspend fun getTurniForDateRange(
        dataInizio: LocalDateTime,
        dataFine: LocalDateTime
    ): List<Turno> {
        return turnoDao.getTurniForDateRange(dataInizio, dataFine)
    }
}
