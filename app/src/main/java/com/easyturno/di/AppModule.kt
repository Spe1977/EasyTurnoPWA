package com.easyturno.di

import android.app.Application
import androidx.room.Room
import com.easyturno.data.database.TurnoDatabase
import com.easyturno.data.database.dao.TurnoDao
import com.easyturno.data.repository.TurnoRepositoryImpl
import com.easyturno.domain.repository.TurnoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

/**
 * Header: Modulo Hilt per la fornitura delle dipendenze a livello di applicazione.
 *
 * Questo modulo definisce come Hilt deve creare e fornire le istanze
 * delle classi fondamentali dell'app, come il database e il repository.
 * L'annotazione `@InstallIn(SingletonComponent::class)` indica che le dipendenze
 * definite qui avranno un ciclo di vita singleton e saranno disponibili
 * in tutta l'applicazione.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Fornisce un'istanza singleton del database Room.
     * Hilt inietterà automaticamente il contesto dell'applicazione.
     */
    @Provides
    @Singleton
    fun provideTurnoDatabase(app: Application): TurnoDatabase {
        return Room.databaseBuilder(
            app,
            TurnoDatabase::class.java,
            "TurnoDatabase.db"
        ).build()
    }

    /**
     * Fornisce un'istanza del DAO dei turni, ottenendola dal database.
     */
    @Provides
    fun provideTurnoDao(db: TurnoDatabase): TurnoDao {
        return db.turnoDao()
    }

    /**
     * Fornisce l'implementazione concreta per l'interfaccia TurnoRepository.
     * Questo metodo dice a Hilt che ogni volta che viene richiesta un'istanza di
     * TurnoRepository, deve fornire un'istanza di TurnoRepositoryImpl.
     */
    @Provides
    @Singleton
    fun provideTurnoRepository(dao: TurnoDao): TurnoRepository {
        return TurnoRepositoryImpl(dao)
    }

    /**
     * Fornisce un'istanza singleton di Json per la serializzazione.
     * `ignoreUnknownKeys = true` rende il parsing più robusto a cambiamenti futuri.
     */
    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true; isLenient = true }
}
