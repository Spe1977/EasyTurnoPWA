package com.easyturno

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application personalizzata per EasyTurno.
 * Necessaria per l'inizializzazione di Hilt (Dependency Injection).
 *
 * L'annotazione @HiltAndroidApp avvia la generazione del codice di Hilt,
 * inclusa una classe base per l'applicazione che funge da container
 * per le dipendenze.
 */
@HiltAndroidApp
class EasyTurnoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Qui è possibile inserire logica di inizializzazione
        // che deve essere eseguita all'avvio dell'app,
        // come l'impostazione di librerie di logging o analytics.
    }
}
