package com.easyturno.data.models

/**
 * Header: Enum che definisce i tipi di ricorrenza per un turno.
 *
 * Questo enum è utilizzato per specificare come un turno si ripete nel tempo.
 * Sarà serializzato e salvato come stringa nel database Room.
 */
enum class RecurrenceType {
    NONE,           // Turno singolo, non si ripete
    DAILY,          // Ogni giorno
    EVERY_N_DAYS,   // Ogni N giorni (es. ogni 3 giorni)
    WEEKLY,         // Settimanale (stesso giorno/i della settimana)
    EVERY_N_WEEKS,  // Ogni N settimane (es. ogni 2 settimane)
    MONTHLY,        // Mensile (stesso giorno del mese)
    QUARTERLY,      // Trimestrale (ogni 3 mesi)
    BIANNUAL,       // Semestrale (ogni 6 mesi)
    YEARLY          // Annuale (stesso giorno e mese)
}
