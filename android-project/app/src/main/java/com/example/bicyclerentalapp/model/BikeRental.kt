package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class BikeRental(
    @PrimaryKey(autoGenerate = true) val idRental: Int = 0,
    val userId: Int,
    val bikeId: Int,
    val startStationId: Int,
    val endStationId: Int? = null,
    val startTime: Long,
    val endTime: Long? = null,
    val totalDurationMinutes: Int? = null,
    val totalPrice: Double? = null,
    val bikePhotoAfterPath: String? = null
)