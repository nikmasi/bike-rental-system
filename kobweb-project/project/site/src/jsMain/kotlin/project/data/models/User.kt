package project.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    var password: String,
    val name: String,
    val loggedIn: Boolean = false
)