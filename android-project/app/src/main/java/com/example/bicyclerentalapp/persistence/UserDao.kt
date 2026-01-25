package com.example.bicyclerentalapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bicyclerentalapp.model.User

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String?): User?

    @Query("SELECT * FROM users WHERE idUser = :id_")
    suspend fun getUser(id_: Int): User?

    @Query("SELECT * FROM users WHERE username = :username AND password =:password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getCurrentUser(): User?

}