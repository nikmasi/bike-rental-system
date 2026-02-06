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

    init {
        processIntent(StationIntent.LoadAllStations)
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

    fun processIntent(intent: StationIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }
}

sealed class StationIntent {
    object LoadAllStations: StationIntent()
}