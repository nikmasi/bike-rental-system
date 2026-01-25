package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class BikeRental(
    @PrimaryKey val idRental: Int,
    val pocetak: String,
    val zavrsetak: String,
    val cena: Double,
    //val bike: Bike,
//    val user: User
)