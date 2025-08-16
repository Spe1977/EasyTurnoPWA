package com.easyturno.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.easyturno.data.database.dao.TurnoDao
import com.easyturno.data.database.entities.Turno

/**
 * Header: Classe principale del database dell'applicazione.
 *
 * Questa classe definisce la configurazione del database Room.
 *
 * - `entities`: Elenca tutte le tabelle (Entity) del database.
 * - `version`: Il numero di versione del database. Deve essere incrementato
 *   ogni volta che lo schema del database viene modificato.
 * - `exportSchema`: Disabilitato per questo progetto per semplicità, ma in un
 *   progetto di produzione è buona pratica esportare lo schema per il versioning.
 * - `typeConverters`: Registra i convertitori di tipo custom per permettere a Room
 *   di gestire tipi di dati non nativi come LocalDateTime.
 */
@Database(
    entities = [Turno::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TurnoDatabase : RoomDatabase() {

    /**
     * Fornisce l'accesso al DAO per le operazioni sui turni.
     * Room genererà l'implementazione di questo metodo.
     */
    abstract fun turnoDao(): TurnoDao

}
