package com.example.bicyclerentalapp.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {

    private val intents = MutableSharedFlow<StationIntent>(replay = 1)
    private val intentsParking = MutableSharedFlow<ParkingZoneIntent>(replay = 1)

    init {
        processIntent(StationIntent.LoadAllStations)
        processIntentParking(ParkingZoneIntent.LoadAllParkingZones)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = intents.flatMapLatest { intent ->
        when (intent) {
            is StationIntent.LoadAllStations -> {
                mapRepository.allStations()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateParking = intentsParking.flatMapLatest { intent ->
        when (intent) {
            is ParkingZoneIntent.LoadAllParkingZones -> {
                mapRepository.allParkingZones()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun processIntent(intent: StationIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    fun processIntentParking(intent: ParkingZoneIntent) {
        viewModelScope.launch {
            intentsParking.emit(intent)
        }
    }

    fun calculateDistance(userLat: Double, userLon: Double, stationLat: Double, stationLon: Double): Float {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(userLat, userLon, stationLat, stationLon, results)
        return results[0] // return m
    }
}

sealed class StationIntent {
    object LoadAllStations: StationIntent()
}

sealed class ParkingZoneIntent {
    object LoadAllParkingZones: ParkingZoneIntent()
}