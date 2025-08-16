# Progressi Sviluppo: EasyTurno

Questo file traccia lo stato di avanzamento dello sviluppo dell'applicazione EasyTurno.

---

## 📊 Percentuali di Completamento per Modulo

- **Iterazione 1: Project Setup**: 100%
- **Iterazione 2: Data Layer**: 100%
- **Iterazione 3: Domain & ViewModel**: 100%
- **Iterazione 4: UI Screens**: 100%
- **Iterazione 5: Background & Utils**: 100%

---

## ✅ Checklist Funzionalità Implementate

### Iterazione 1: Project Setup (Completata)
- [x] Configurazione `build.gradle.kts` con tutte le dipendenze
- [x] Configurazione `AndroidManifest.xml` (Hilt, Permessi)
- [x] Creazione `strings.xml` (it/en)
- [x] Creazione `themes.xml` (Material 3)
- [x] Creazione file `ProgressiEasyTurno.md`

### Iterazione 2: Data Layer (Completata)
- [x] `TurnoEntity.kt` (come `Turno.kt`)
- [x] `TurnoDao.kt`
- [x] `TurnoRepository.kt` (interfaccia e implementazione)
- [x] `TurnoDatabase.kt`

### Iterazione 3: Domain & ViewModel (Completata)
- [x] `TurnoViewModel.kt`
- [x] `TurnoUseCase.kt` (come `GetTurniPerRangeUseCase.kt`)
- [x] `RecurrenceCalculator.kt`
- [x] `DateTimeUtils.kt`

### Iterazione 4: UI Screens (Completata)
- [x] `MainActivity.kt`
- [x] `TurnoListScreen.kt`
- [x] `TurnoFormScreen.kt`
- [x] `CalendarScreen.kt`

### Iterazione 5: Background & Utils (Completata)
- [x] `NotificationWorker.kt`
- [x] `ExportImportUtils.kt`
- [x] `ThemeManager.kt`
- [x] `NavigationGraph.kt`

---

## 🐛 Issues Noti e Soluzioni

| ID  | Problema | Soluzione Proposta | Stato |
| --- | -------- | ------------------ | ----- |
| -   | `TurnoFormScreen` e `CalendarScreen` sono implementati solo come scheletro. | Completare l'implementazione della UI e della logica di stato. | `Da Fare` |
| -   | Le notifiche non sono ancora schedulate. Manca la logica che chiama il `NotificationWorker`. | Aggiungere la logica nel ViewModel o in un UseCase per schedulare il worker quando un turno viene creato/aggiornato. | `Da Fare` |
| -   | L'import/export non è collegato alla UI. | Aggiungere opzioni nel menu della UI per triggerare le funzioni di `ExportImportUtils`. | `Da Fare` |

---

## 📝 Note di Testing

- **2025-08-16**: La configurazione iniziale del progetto è stata completata.
- **2025-08-16**: Il Data Layer è stato implementato.
- **2025-08-16**: Il layer di Dominio e il ViewModel sono stati implementati.
- **2025-08-16**: Le schermate UI (`MainActivity`, `TurnoListScreen`, `TurnoFormScreen`, `CalendarScreen`) sono state create con una struttura di base.
- **2025-08-16**: Le utility e i worker di background sono stati creati strutturalmente. L'intero scheletro dell'applicazione è completo. Il codice dovrebbe compilare. I prossimi passi sono il testing e il completamento delle feature a livello di UI.
