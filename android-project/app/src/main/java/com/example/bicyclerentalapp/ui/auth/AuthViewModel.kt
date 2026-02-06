package com.example.bicyclerentalapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bicyclerentalapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val intents = MutableSharedFlow<UserIntent>(replay = 1)

    private val _signUpResult = MutableStateFlow<User?>(null)
    val signUpResult = _signUpResult

    private val _logInResult = MutableStateFlow<User?>(null)
    val logInResult = _logInResult

    private val _errorState = MutableStateFlow<AuthError?>(null)
    val errorState = _errorState

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun isStrongPassword(password: String): Boolean {
        val hasUpper = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        return password.length >= 8 && hasUpper && hasDigit
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = intents.flatMapLatest { intent ->
        when(intent){
            is UserIntent.LoadCurrentUser -> {
                authRepository.currentUser()
                    .onEach { user ->
                        _logInResult.value = user
                    }
            }
            is UserIntent.Login -> {
                if (intent.username.isBlank() || intent.password.isBlank()) {
                    _errorState.value = AuthError.EmptyFields
                    MutableSharedFlow()
                } else {
                    authRepository.login(intent.username, intent.password)
                        .onEach { user ->
                            if (user == null) {
                                _errorState.value = AuthError.InvalidCredentials
                            } else {
                                _logInResult.value = user
                                _errorState.value = null
                            }
                        }
                }
            }
            UserIntent.LoadCurrentUser -> {
                authRepository.currentUser()
                    .onEach { user ->
                        _logInResult.value = user
                        _errorState.value = null
                    }
            }

            is UserIntent.SignUp -> {
                when {
                    intent.username.isBlank() ||
                            intent.password.isBlank() ||
                            intent.firstname.isBlank() ||
                            intent.lastname.isBlank() ||
                            intent.phone.isBlank() ||
                            intent.email.isBlank() -> {
                        _errorState.value = AuthError.EmptyFields
                        MutableSharedFlow() // stop flow
                    }

                    !isValidEmail(intent.email) -> {
                        _errorState.value = AuthError.InvalidEmail
                        MutableSharedFlow()
                    }

                    !isStrongPassword(intent.password) -> {
                        _errorState.value = AuthError.WeakPassword
                        MutableSharedFlow()
                    }

                    else -> {
                        authRepository.signUp(
                            username = intent.username,
                            password = intent.password,
                            firstname = intent.firstname,
                            lastname = intent.lastname,
                            phone = intent.phone,
                            email = intent.email
                        ).onEach { user ->
                            _signUpResult.value = user
                            _errorState.value = null
                        }
                    }
                }
            }
            is UserIntent.EditProfile -> {
                authRepository.editProfile(intent.user)
                    .onEach { updatedUser ->
                        _logInResult.value = updatedUser
                        _errorState.value = null
                    }
            }
            else -> authRepository.currentUser()

            //UserIntent.LoadCurrentUser -> authRepository.currentUser()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    fun processIntent(intent: UserIntent){
        intents.tryEmit(intent)
    }

}

sealed class UserIntent {
    data class Login(val username: String, val password: String): UserIntent()
    data class SignUp(
        val username: String,
        val password: String,
        val firstname: String,
        val lastname: String,
        val phone: String,
        val email: String
    ): UserIntent()
    object LoadCurrentUser: UserIntent()
    data class EditProfile(val user: User): UserIntent()
}

sealed class AuthError {
    object EmptyFields : AuthError()
    object InvalidEmail : AuthError()
    object WeakPassword : AuthError()
    object InvalidCredentials : AuthError()
}
