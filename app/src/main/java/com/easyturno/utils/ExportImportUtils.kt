package com.easyturno.utils

import android.content.Context
import android.net.Uri
import com.easyturno.data.database.entities.Turno
import com.easyturno.domain.repository.TurnoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * Header: Utility per l'esportazione e l'importazione dei dati dei turni in formato JSON.
 *
 * Questa classe gestisce la logica di serializzazione e deserializzazione dei dati
 * e l'interazione con il file system tramite URI forniti dallo Storage Access Framework (SAF).
 */
class ExportImportUtils @Inject constructor(
    private val turnoRepository: TurnoRepository,
    private val json: Json // Hilt può iniettare un'istanza di Json se la definiamo in un modulo
) {

    /**
     * Esporta tutti i turni in un file JSON.
     *
     * @param context Il contesto dell'applicazione.
     * @param uri L'URI del file in cui scrivere, ottenuto tramite SAF.
     * @return Boolean che indica il successo dell'operazione.
     */
    suspend fun exportTurni(context: Context, uri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val turni = turnoRepository.getAllTurni().first()
            val jsonString = json.encodeToString(turni)
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(jsonString.toByteArray())
            }
            true
        } catch (e: Exception) {
            // Log a e
            false
        }
    }

    /**
     * Importa i turni da un file JSON.
     *
     * @param context Il contesto dell'applicazione.
     * @param uri L'URI del file da cui leggere, ottenuto tramite SAF.
     * @return Boolean che indica il successo dell'operazione.
     */
    suspend fun importTurni(context: Context, uri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val stringBuilder = StringBuilder()
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                    }
                }
            }
            val jsonString = stringBuilder.toString()
            val turniImportati = json.decodeFromString<List<Turno>>(jsonString)

            // Validazione base: assicurarsi che gli ID siano a 0 per evitare conflitti.
            turniImportati.forEach { turno ->
                turnoRepository.insertTurno(turno.copy(id = 0))
            }
            true
        } catch (e: Exception) {
            // Log a e (es. JsonDecodingException)
            false
        }
    }
}

/**
 * Forniremo l'istanza di Json tramite un modulo Hilt.
 * Aggiungo qui un commento per ricordarlo.
 *
 * In AppModule.kt:
 * @Provides
 * @Singleton
 * fun provideJson(): Json = Json { ignoreUnknownKeys = true; isLenient = true }
 */
