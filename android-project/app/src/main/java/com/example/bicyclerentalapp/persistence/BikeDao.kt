package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bicyclerentalapp.model.Bike

@Dao
interface BikeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBike(bike: Bike)

    @Query("SELECT * FROM Bike WHERE idBike = :id_")
    suspend fun getBike(id_: Int): Bike?
}