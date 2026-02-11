package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bicyclerentalapp.model.ParkingZone

@Dao
interface ParkingZoneDao{
    @Query("SELECT * FROM ParkingZone")
    suspend fun getAllParkingZones(): List<ParkingZone>

    @Insert
    suspend fun insertParkingZone(zone: ParkingZone): Long

}