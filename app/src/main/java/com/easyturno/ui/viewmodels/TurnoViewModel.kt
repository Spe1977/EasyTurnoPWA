package com.easyturno.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyturno.data.database.entities.Turno
import com.easyturno.domain.repository.TurnoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Header: ViewModel per la gestione dei dati relativi ai turni.
 *
 * Questo ViewModel funge da intermediario tra il Repository e la UI.
 * È responsabile di preparare e gestire i dati per le schermate dei turni,
 * esponendoli tramite StateFlow in modo che la UI possa reagire ai cambiamenti.
 *
 * @property repository Il repository per l'accesso ai dati dei turni, iniettato da Hilt.
 */
@HiltViewModel
class TurnoViewModel @Inject constructor(
    private val repository: TurnoRepository
) : ViewModel() {

    /**
     * Un Flow di stato che espone la lista di tutti i turni.
     * La UI osserverà questo Flow per aggiornarsi automaticamente.
     * - `stateIn`: Converte un Flow normale in uno StateFlow, che mantiene l'ultimo valore.
     * - `viewModelScope`: Il CoroutineScope legato al ciclo di vita del ViewModel.
     * - `SharingStarted.WhileSubscribed(5000)`: Il Flow a monte è attivo solo se ci sono iscritti,
     *   con un timeout di 5 secondi per resistere a brevi interruzioni (es. rotazione schermo).
     */
    val tuttiITurni: StateFlow<List<Turno>> = repository.getAllTurni()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    /**
     * Inserisce un nuovo turno nel database.
     * L'operazione viene eseguita in una coroutine all'interno del viewModelScope.
     */
    fun insertTurno(turno: Turno) {
        viewModelScope.launch {
            repository.insertTurno(turno)
        }
    }

    /**
     * Aggiorna un turno esistente.
     */
    fun updateTurno(turno: Turno) {
        viewModelScope.launch {
            repository.updateTurno(turno)
        }
    }

    /**
     * Cancella un turno.
     */
    fun deleteTurno(turno: Turno) {
        viewModelScope.launch {
            repository.deleteTurno(turno)
        }
    }
}
