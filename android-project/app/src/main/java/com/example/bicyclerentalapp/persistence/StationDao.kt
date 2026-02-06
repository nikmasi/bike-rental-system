package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bicyclerentalapp.model.Station

@Dao
interface StationDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStation(station: Station)

    @Query("SELECT * FROM Station WHERE idStation = :id_")
    suspend fun getStation(id_: Int): Station?

    @Query("SELECT * FROM Station")
    suspend fun getAllStations(): List<Station>
}