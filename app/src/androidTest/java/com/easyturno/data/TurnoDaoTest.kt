package com.easyturno.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.easyturno.data.database.TurnoDatabase
import com.easyturno.data.database.dao.TurnoDao
import com.easyturno.data.database.entities.Turno
import com.easyturno.data.models.RecurrenceType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TurnoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var turnoDao: TurnoDao
    private lateinit var db: TurnoDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TurnoDatabase::class.java
        ).build()
        turnoDao = db.turnoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTurno() = runBlocking {
        val turno = Turno(1, "Test", LocalDateTime(2025,1,1,8,0), LocalDateTime(2025,1,1,16,0), RecurrenceType.NONE, null)
        turnoDao.insertTurno(turno)
        val retrievedTurno = turnoDao.getTurnoById(1)
        assertEquals(turno.titolo, retrievedTurno?.titolo)
    }

    @Test
    @Throws(Exception::class)
    fun getAllTurni() = runBlocking {
        val turno1 = Turno(1, "Test 1", LocalDateTime(2025,1,1,8,0), LocalDateTime(2025,1,1,16,0), RecurrenceType.NONE, null)
        val turno2 = Turno(2, "Test 2", LocalDateTime(2025,1,2,8,0), LocalDateTime(2025,1,2,16,0), RecurrenceType.NONE, null)
        turnoDao.insertTurno(turno1)
        turnoDao.insertTurno(turno2)
        val allTurni = turnoDao.getAllTurni().first()
        assertEquals(2, allTurni.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTurno() = runBlocking {
        val turno = Turno(1, "Test", LocalDateTime(2025,1,1,8,0), LocalDateTime(2025,1,1,16,0), RecurrenceType.NONE, null)
        turnoDao.insertTurno(turno)
        turnoDao.deleteTurno(turno)
        val retrievedTurno = turnoDao.getTurnoById(1)
        assertNull(retrievedTurno)
    }

    @Test
    @Throws(Exception::class)
    fun updateTurno() = runBlocking {
        val turno = Turno(1, "Test Originale", LocalDateTime(2025,1,1,8,0), LocalDateTime(2025,1,1,16,0), RecurrenceType.NONE, null)
        turnoDao.insertTurno(turno)
        val updatedTurno = turno.copy(titolo = "Test Modificato")
        turnoDao.updateTurno(updatedTurno)
        val retrievedTurno = turnoDao.getTurnoById(1)
        assertEquals("Test Modificato", retrievedTurno?.titolo)
    }
}
