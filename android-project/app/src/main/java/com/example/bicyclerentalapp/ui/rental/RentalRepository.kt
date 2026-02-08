package com.example.bicyclerentalapp.ui.rental

import androidx.annotation.WorkerThread
import com.example.bicyclerentalapp.model.BikeRental
import com.example.bicyclerentalapp.model.BikeReturnReport
import com.example.bicyclerentalapp.persistence.BikeRentalDao
import com.example.bicyclerentalapp.persistence.BikeReturnReportDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RentalRepository @Inject constructor(
    private val bikeRentalDao: BikeRentalDao,
    private val bikeReturnReportDao: BikeReturnReportDao
){
    @WorkerThread
    fun insertBikeRental(bikeRental: BikeRental) = flow {
        val generatedId = bikeRentalDao.insertBikeRental(bikeRental)
        val rentalWithId = bikeRental.copy(idRental = generatedId.toInt())
        emit(rentalWithId)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun updateBikeRental(bikeRental: BikeRental) = flow {
        bikeRentalDao.updateBikeRental(bikeRental)
        emit(bikeRental)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun allRentals(userId: Int) = flow {
        val rentals = bikeRentalDao.getAllRental(userId)
        emit(rentals)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getAllRentalsWithReports(userId: Int): Flow<Map<BikeRental, BikeReturnReport?>> {
        return bikeRentalDao.getRentalsWithReports(userId)
    }

    @WorkerThread
    fun insertBikeRentalProblem(bikeReturnReport: BikeReturnReport) = flow {
        val generatedId = bikeReturnReportDao.insertBikeReturnReport(bikeReturnReport)
        val rentalWithId = bikeReturnReport.copy(idBikeReturn = generatedId.toInt())
        emit(rentalWithId)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun updateBikeRentalProblem(bikeReturnReport: BikeReturnReport) = flow {
        bikeReturnReportDao.updateBikeReturnReport(bikeReturnReport)
        emit(bikeReturnReport)
    }.flowOn(Dispatchers.IO)
}