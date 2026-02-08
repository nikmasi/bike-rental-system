package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bicyclerentalapp.model.BikeRental
import com.example.bicyclerentalapp.model.BikeReturnReport
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeRentalDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBikeRental(bikeRental: BikeRental): Long

    @Query("SELECT * FROM BikeRental WHERE idRental = :id_")
    suspend fun getBikeRental(id_: Int): BikeRental?

    @Query("SELECT * FROM BikeRental WHERE userId = :idUser_")
    suspend fun getAllRental(idUser_: Int): List<BikeRental>

    @Query("""SELECT * FROM BikeRental LEFT JOIN BikeReturnReport ON BikeRental.idRental = BikeReturnReport.rentalId 
    WHERE BikeRental.userId = :userId ORDER BY BikeRental.startTime DESC""")
    fun getRentalsWithReports(userId: Int): Flow<Map<BikeRental, BikeReturnReport?>>

    @Update
    suspend fun updateBikeRental(bikeRental: BikeRental)
}