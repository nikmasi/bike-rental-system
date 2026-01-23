package project.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val password: String,
    val name: String,
    val loggedIn: Boolean = false
)