package com.example.bicyclerentalapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParkingZone(
    @PrimaryKey val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Double,
    val hasCharging: Boolean
)
