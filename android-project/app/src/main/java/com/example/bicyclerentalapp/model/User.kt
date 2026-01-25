package com.example.bicyclerentalapp.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
@Immutable
data class User(
    @PrimaryKey(autoGenerate = true) val idUser: Int,
    val firstname: String,
    val lastname: String,
    val phone: String,
    val email: String,
    val username: String,
    val password: String,
)