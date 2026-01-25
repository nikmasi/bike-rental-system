package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bicyclerentalapp.model.BikeRental

@Dao
interface BikeRentalDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBikeRental(bikeRental: BikeRental)

    @Query("SELECT * FROM BikeRental WHERE idRental = :id_")
    suspend fun getBikeRental(id_: Int): BikeRental?
}