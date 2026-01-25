package com.example.bicyclerentalapp.ui.auth

import androidx.annotation.WorkerThread
import com.example.bicyclerentalapp.model.User
import com.example.bicyclerentalapp.persistence.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.security.MessageDigest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val userDao: UserDao
){
    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    @WorkerThread
    fun getUserByUsername(username: String?) = flow {
        val user = userDao.getUserByUsername(username)
        emit(user)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun login(username: String, password: String) = flow {
        val hashed = hashPassword(password)
        val user = userDao.login(username, hashed)
        emit(user)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun signUp(username: String, password: String, firstname: String, lastname:String, phone:String, email:String) = flow {
        val hashed = hashPassword(password)
        val user = User(
            username = username, password = hashed,
            firstname = firstname,
            lastname = lastname,
            phone = phone,
            email = email,
            idUser = 0
        )
        userDao.insertUser(user)
        emit(user)
    }.flowOn(Dispatchers.IO)
    @WorkerThread
    fun currentUser() = flow {
        val user = userDao.getCurrentUser()
        emit(user)
    }.flowOn(Dispatchers.IO)

}