package com.example.bicyclerentalapp.ui.rental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicyclerentalapp.model.Bike
import com.example.bicyclerentalapp.model.Station
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor() : ViewModel() {

    private val _selectedStation = MutableStateFlow<Station?>(null)
    val selectedStation = _selectedStation.asStateFlow()

    private val _selectedBike = MutableStateFlow<Bike?>(null)
    val selectedBike = _selectedBike.asStateFlow()

    // time
    private val _secondsActive = MutableStateFlow(0L)
    val secondsActive = _secondsActive.asStateFlow()

    private var timerJob: Job? = null

    fun setRentalData(station: Station) {
        _selectedStation.value = station
        //_selectedBike.value = bike
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _secondsActive.value += 1
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }


    fun formatSecondsToMinutes(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        return "${minutes} min"
    }

    fun stopRental() {
        timerJob?.cancel()

        val finalTime = _secondsActive.value
        //val finalPrice = calculatePrice(finalTime)
    }

    fun confirmRental(stationId: Int, isElectro: Boolean, qrResult: String) {
        // qr logic
        // get first bike
        _selectedBike.value = Bike(
            idBike = (100..999).random(), // simulation
            stationId = stationId,
            isElectro = isElectro,
            batteryLevel = if (isElectro) 85 else null
        )
        startTimer()
    }
}