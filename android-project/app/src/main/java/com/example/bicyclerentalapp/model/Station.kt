package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Station(
    @PrimaryKey val idStation: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,

    val classicCapacity: Int,  // normal bikes
    val electroCapacity: Int, // electrical
    val classicBikesCount: Int,
    val electroBikesCount: Int,

    val hasCharging: Boolean = true,

    val classicPrice: Double = 1.50,
    val electroPrice: Double = 1.20
)