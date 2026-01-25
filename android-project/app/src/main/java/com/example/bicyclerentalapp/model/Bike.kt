package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Bike(
    @PrimaryKey val idBike: Int,
    val tip: String,
    val lokacija: String,
    val cena: Double,
    val photo:String,
    val status: String = "dostupan"
)