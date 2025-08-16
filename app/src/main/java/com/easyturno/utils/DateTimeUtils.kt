package com.easyturno.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Header: Oggetto di utilità per la gestione di date e orari.
 *
 * Fornisce funzioni helper per la formattazione e la conversione
 * di oggetti `kotlinx.datetime`. L'uso di un oggetto singleton (`object`)
 * rende queste funzioni facilmente accessibili in tutta l'app senza
 * bisogno di istanziare la classe.
 */
object DateTimeUtils {

    // Formatter per mostrare data e ora (es. "16 ago 2025, 09:30")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", Locale.ITALIAN)

    // Formatter per mostrare solo l'ora (es. "09:30")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ITALIAN)

    // Formatter per mostrare solo la data (es. "16 agosto 2025")
    private val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ITALIAN)

    /**
     * Formatta un LocalDateTime in una stringa leggibile (data e ora).
     * @param dateTime L'oggetto da formattare.
     * @return La stringa formattata, o una stringa vuota se l'input è nullo.
     */
    fun formatDateTime(dateTime: LocalDateTime?): String {
        return dateTime?.toJavaLocalDateTime()?.format(dateTimeFormatter) ?: ""
    }

    /**
     * Formatta un LocalDateTime in una stringa leggibile (solo ora).
     * @param dateTime L'oggetto da formattare.
     * @return La stringa formattata, o una stringa vuota se l'input è nullo.
     */
    fun formatTime(dateTime: LocalDateTime?): String {
        return dateTime?.toJavaLocalDateTime()?.format(timeFormatter) ?: ""
    }

    /**
     * Formatta una LocalDate in una stringa leggibile (solo data).
     * @param date L'oggetto da formattare.
     * @return La stringa formattata, o una stringa vuota se l'input è nullo.
     */
    fun formatDate(date: LocalDate?): String {
        return date?.toJavaLocalDate()?.format(dateFormatter) ?: ""
    }

    /**
     * Converte un timestamp in millisecondi (Long) in un LocalDateTime.
     * @param millis Il timestamp.
     * @param timeZone La timezone di riferimento (default: sistema).
     * @return L'oggetto LocalDateTime corrispondente.
     */
    fun millisToLocalDateTime(millis: Long, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
        return Instant.fromEpochMilliseconds(millis).toLocalDateTime(timeZone)
    }

    /**
     * Converte un LocalDateTime in un timestamp in millisecondi (Long).
     * @param dateTime L'oggetto da convertire.
     * @param timeZone La timezone di riferimento (default: sistema).
     * @return Il timestamp corrispondente.
     */
    fun localDateTimeToMillis(dateTime: LocalDateTime, timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
        return dateTime.toInstant(timeZone).toEpochMilliseconds()
    }
}
