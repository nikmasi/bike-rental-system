package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bicyclerentalapp.model.BikeReturnReport

@Dao
interface BikeReturnReportDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBikeReturnReport(bikeReturnReport: BikeReturnReport): Long

    @Query("SELECT * FROM BikeReturnReport WHERE idBikeReturn = :id_")
    suspend fun getBikeReturnReport(id_: Int): BikeReturnReport?

    @Update
    suspend fun updateBikeReturnReport(bikeReturnReport: BikeReturnReport)
}