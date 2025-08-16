package com.easyturno.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.easyturno.R
import com.easyturno.domain.repository.TurnoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Header: Worker per la gestione e la visualizzazione delle notifiche pre-turno.
 *
 * Questo worker viene schedulato da WorkManager per essere eseguito in background
 * e mostrare una notifica all'utente prima dell'inizio di un turno.
 */
@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val turnoRepository: TurnoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val turnoId = inputData.getLong(KEY_TURNO_ID, -1L)
        if (turnoId == -1L) {
            return Result.failure()
        }

        return try {
            val turno = turnoRepository.getTurnoById(turnoId)
            if (turno != null) {
                sendNotification(turno.titolo, "Il tuo turno sta per iniziare.")
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun sendNotification(titolo: String, messaggio: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crea il canale di notifica per Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notifiche Turni",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canale per le notifiche dei turni di lavoro"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Costruisce la notifica
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Placeholder: usare un'icona di notifica appropriata
            .setContentTitle(titolo)
            .setContentText(messaggio)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {
        const val KEY_TURNO_ID = "TURNO_ID"
        const val CHANNEL_ID = "EASYTURNO_CHANNEL"
    }
}
