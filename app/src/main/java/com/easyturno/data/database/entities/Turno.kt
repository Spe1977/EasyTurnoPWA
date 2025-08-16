package com.easyturno.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easyturno.data.models.RecurrenceType
import kotlinx.datetime.LocalDateTime

/**
 * Header: Entity per la tabella "turni" del database Room.
 *
 * Rappresenta un singolo turno di lavoro.
 *
 * @property id ID univoco autoincrementale del turno.
 * @property titolo Nome o descrizione breve del turno (es. "Mattina", "Notte H24").
 * @property dataInizio Timestamp di inizio del turno.
 * @property dataFine Timestamp di fine del turno.
 * @property ricorrenza Il tipo di ricorrenza (NONE, DAILY, WEEKLY, etc.).
 * @property parametriRicorrenza Stringa JSON per contenere dati aggiuntivi sulla ricorrenza
 *                                (es. {"days": [1,3,5]} per settimanale, {"n": 3} per ogni N giorni).
 * @property notificaAttiva Flag per abilitare/disabilitare la notifica per questo turno.
 * @property note Campo testuale per note aggiuntive.
 */
@Entity(tableName = "turni")
data class Turno(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titolo: String,
    val dataInizio: LocalDateTime,
    val dataFine: LocalDateTime,
    val ricorrenza: RecurrenceType,
    val parametriRicorrenza: String?, // Esempio JSON: {"every_n_days": 3} o {"weeks_of_month": [1, 3]}
    val notificaAttiva: Boolean = true,
    val note: String? = null
)
