package com.easyturno.data.database

import androidx.room.TypeConverter
import com.easyturno.data.models.RecurrenceType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

/**
 * Header: Type Converters per il database Room.
 *
 * Room può salvare solo tipi di dati primitivi. Questi metodi dicono a Room
 * come convertire tipi complessi (come LocalDateTime e RecurrenceType) in tipi
 * primitivi per la persistenza e come riconvertirli indietro quando si leggono i dati.
 */
class Converters {

    /**
     * Converte un timestamp String (in formato ISO 8601) in un oggetto LocalDateTime.
     * @param value La stringa da convertire, può essere nulla.
     * @return Un oggetto LocalDateTime o null se l'input era nullo.
     */
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.toLocalDateTime()
    }

    /**
     * Converte un oggetto LocalDateTime in una stringa (formato ISO 8601) per la memorizzazione.
     * @param date L'oggetto LocalDateTime da convertire, può essere nullo.
     * @return Una rappresentazione stringa della data o null se l'input era nullo.
     */
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    /**
     * Converte una stringa nel corrispondente valore enum RecurrenceType.
     * @param value La stringa che rappresenta il nome dell'enum.
     * @return L'oggetto RecurrenceType o null se l'input era nullo.
     */
    @TypeConverter
    fun toRecurrenceType(value: String?): RecurrenceType? {
        return value?.let { RecurrenceType.valueOf(it) }
    }

    /**
     * Converte un enum RecurrenceType nella sua rappresentazione stringa (il suo nome).
     * @param recurrenceType L'enum da convertire.
     * @return Il nome dell'enum come stringa o null se l'input era nullo.
     */
    @TypeConverter
    fun fromRecurrenceType(recurrenceType: RecurrenceType?): String? {
        return recurrenceType?.name
    }
}
