package com.example.bicyclerentalapp.ui.map

import androidx.annotation.WorkerThread
import com.example.bicyclerentalapp.persistence.ParkingZoneDao
import com.example.bicyclerentalapp.persistence.StationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val stationDao: StationDao,
    private val parkingZoneDao: ParkingZoneDao
){
    @WorkerThread
    fun currentStation(id:Int) = flow {
        val station = stationDao.getStation(id)
        emit(station)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun allStations() = flow {
        val stations = stationDao.getAllStations()
        emit(stations)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun allParkingZones() = flow {
        val parkingZones = parkingZoneDao.getAllParkingZones()
        emit(parkingZones)
    }.flowOn(Dispatchers.IO)
}