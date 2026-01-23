package project.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Rental(
    val id: Int,
    val start: String,
    val end: String,
    val price: Double,
    val bike: Bike,
    val user: User
)