package com.easyturno.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.easyturno.data.database.entities.Turno
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

/**
 * Header: DAO (Data Access Object) per l'entità Turno.
 *
 * Questa interfaccia definisce tutte le operazioni di accesso al database
 * per la tabella "turni". Room si occuperà di generare l'implementazione concreta.
 * L'uso di Flow permette alla UI di reagire automaticamente ai cambiamenti nel database.
 */
@Dao
interface TurnoDao {

    /**
     * Inserisce un nuovo turno nel database.
     * Se un turno con lo stesso ID esiste già, viene sostituito.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTurno(turno: Turno)

    /**
     * Aggiorna un turno esistente.
     */
    @Update
    suspend fun updateTurno(turno: Turno)

    /**
     * Cancella un turno dal database.
     */
    @Delete
    suspend fun deleteTurno(turno: Turno)

    /**
     * Recupera un singolo turno tramite il suo ID.
     * @return Il Turno corrispondente o null se non trovato.
     */
    @Query("SELECT * FROM turni WHERE id = :id")
    suspend fun getTurnoById(id: Long): Turno?

    /**
     * Recupera tutti i turni presenti nel database, ordinati per data di inizio.
     * @return Un Flow che emette la lista di tutti i turni ogni volta che i dati cambiano.
     */
    @Query("SELECT * FROM turni ORDER BY dataInizio ASC")
    fun getAllTurni(): Flow<List<Turno>>

    /**
     * Recupera tutti i turni che iniziano in un dato intervallo di date.
     * Utile per le visualizzazioni giornaliere, settimanali e mensili.
     * @param dataInizio La data di inizio dell'intervallo.
     * @param dataFine La data di fine dell'intervallo.
     * @return Una lista di turni che ricadono nell'intervallo specificato.
     */
    @Query("SELECT * FROM turni WHERE dataInizio BETWEEN :dataInizio AND :dataFine ORDER BY dataInizio ASC")
    suspend fun getTurniForDateRange(dataInizio: LocalDateTime, dataFine: LocalDateTime): List<Turno>
}
