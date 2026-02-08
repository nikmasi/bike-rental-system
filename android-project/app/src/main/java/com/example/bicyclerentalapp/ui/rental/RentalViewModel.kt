package com.example.bicyclerentalapp.ui.rental

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicyclerentalapp.model.Bike
import com.example.bicyclerentalapp.model.BikeRental
import com.example.bicyclerentalapp.model.BikeReturnReport
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
class RentalViewModel @Inject constructor(
    private val  rentalRepository: RentalRepository
) : ViewModel() {

    private val _selectedStation = MutableStateFlow<Station?>(null)
    val selectedStation = _selectedStation.asStateFlow()

    private val _selectedBike = MutableStateFlow<Bike?>(null)
    val selectedBike = _selectedBike.asStateFlow()

    // rental
    private val _activeRental = MutableStateFlow<BikeRental?>(null)
    val activeRental = _activeRental.asStateFlow()

    // time
    private val _secondsActive = MutableStateFlow(0L)
    val secondsActive = _secondsActive.asStateFlow()

    private val _allRentals = MutableStateFlow<List<BikeRental>>(emptyList())
    val allRentals = _allRentals.asStateFlow()

    private val _allRentalsWithReports = MutableStateFlow<List<RentalWithReport>>(emptyList())
    val allRentalsWithReports = _allRentalsWithReports.asStateFlow()


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

    fun confirmRental(stationId: Int, isElectro: Boolean, qrResult: String, userId: Int) {
        // qr logic
        // get first bike
        _selectedBike.value = Bike(
            idBike = (100..999).random(), // simulation
            stationId = stationId,
            isElectro = isElectro,
            batteryLevel = if (isElectro) 85 else null
        )


        val bikeRental = BikeRental(
            idRental = 0,
            userId = userId,
            bikeId = _selectedBike.value?.idBike ?: 0,
            startStationId = stationId,
            endStationId = null,
            startTime = System.currentTimeMillis(),
            endTime = null,
            totalDurationMinutes = null,
            totalPrice = null,
            bikePhotoAfterPath = null
        )
        startTimer()


        viewModelScope.launch {
            rentalRepository.insertBikeRental(bikeRental)
                .collect { savedRental ->
                    // when flow emit value then is in room
                    _activeRental.value = savedRental
                }
        }
    }

    fun completeRental(endStationId: Int) {
        val currentRental = _activeRental.value ?: return
        val endTime = System.currentTimeMillis()
        val durationSeconds = _secondsActive.value

        val minutes = (durationSeconds / 60.0).let { Math.ceil(it).toInt() }.coerceAtLeast(1)

        val basePrice = if (selectedBike.value?.isElectro == true) {
            _selectedStation.value?.electroPrice ?: 0.0
        } else {
            _selectedStation.value?.classicPrice ?: 0.0
        }

        val multiplier = (minutes + 59) / 60
        val totalPrice = multiplier * basePrice

        val finishedRental = currentRental.copy(
            endStationId = endStationId,
            endTime = endTime,
            totalDurationMinutes = minutes,
            totalPrice = totalPrice,
        )

        Log.d("CURRENT RENTAL", currentRental.toString())

        viewModelScope.launch {
            rentalRepository.updateBikeRental(finishedRental).collect {
                _activeRental.value = it
                timerJob?.cancel()
            }
        }
    }

    fun setPhotoPath(photoPath: String?){
        val currentRental = _activeRental.value ?: return

        val finishedRental = currentRental.copy(
            bikePhotoAfterPath = photoPath
        )
        viewModelScope.launch {
            rentalRepository.updateBikeRental(finishedRental).collect {
                _activeRental.value = it
                timerJob?.cancel()
            }
        }
    }

    fun allRentals(userId:Int){
        viewModelScope.launch {
            rentalRepository.allRentals(userId).collect {
                _allRentals.value = it
            }
        }
    }

    fun allRentalsWithReports(userId: Int) {
        viewModelScope.launch {
            rentalRepository.getAllRentalsWithReports(userId).collect { rentalMap ->
                val transformedList = rentalMap.entries.map { entry ->
                    RentalWithReport(
                        rental = entry.key,
                        report = entry.value
                    )
                }
                _allRentalsWithReports.value = transformedList
            }
        }
    }

    // Problem
    private val _activeReport = MutableStateFlow<BikeReturnReport?>(null)
    val activeReport = _activeReport.asStateFlow()

    private val _reportPhotos = MutableStateFlow<List<String>>(emptyList())
    val reportPhotos = _reportPhotos.asStateFlow()

    fun addReportPhoto(path: String) {
        _reportPhotos.value = _reportPhotos.value + path
    }

    fun clearReportPhotos() {
        _reportPhotos.value = emptyList()
    }

    fun submitReport(rentalId: Int, description: String) {
        val photosString = _reportPhotos.value.joinToString("|") // join paths

        val report = BikeReturnReport(
            rentalId = rentalId,
            userId = _activeRental.value?.userId ?: 0,
            photosPaths = photosString,
            hasIssue = description.isNotBlank(),
            issueDescription = description
        )

        viewModelScope.launch {
            rentalRepository.insertBikeRentalProblem(report)
                .collect { savedRental ->
                    _activeReport.value = savedRental
                }
        }
    }
}

data class RentalWithReport(
    val rental: BikeRental,
    val report: BikeReturnReport? = null
)