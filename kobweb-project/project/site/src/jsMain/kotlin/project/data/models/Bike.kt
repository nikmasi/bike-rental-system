package project.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Bike(
    val id: Int,
    val type: String,
    val location: String,
    val price: Double,
    val photo:String,
    val status: String = "Available"
)