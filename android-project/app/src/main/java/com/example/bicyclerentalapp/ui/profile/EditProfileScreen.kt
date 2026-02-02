package com.example.bicyclerentalapp.ui.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bicyclerentalapp.ui.auth.AuthError
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.components.AuthTextField
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper

@Composable
fun EditProfileScreen(viewModel: AuthViewModel){

    val user by viewModel.logInResult.collectAsState()

    val error by viewModel.errorState.collectAsState()

    val isEmailError = error == AuthError.InvalidEmail
    val isPasswordError = error == AuthError.WeakPassword
    val isEmptyError = error == AuthError.EmptyFields

    var username by remember { mutableStateOf(user?.username.toString()) }
    var firstname by remember { mutableStateOf(user?.firstname.toString()) }
    var lastname by remember { mutableStateOf(user?.lastname.toString()) }
    var phone by remember { mutableStateOf(user?.phone.toString()) }
    var email by remember { mutableStateOf(user?.email.toString()) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            val message = when (it) {
                AuthError.EmptyFields ->
                    "All fields are required"
                AuthError.InvalidEmail ->
                    "Invalid email address"
                AuthError.WeakPassword ->
                    "Password must be at least 8 characters, contain a number and uppercase letter"
                AuthError.InvalidCredentials ->
                    "Wrong username or password"
            }

            snackbarHostState.showSnackbar(message)
        }
    }


    ScreenWrapper(title = "My profile") {
        // icon person
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(bottom = 24.dp).size(70.dp)
        )

            AuthTextField(
                value = firstname,
                onValueChange = { firstname = it },
                placeholder =  "Firstname"
            )

            AuthTextField(
                value = lastname,
                onValueChange = { lastname = it },
                placeholder =  "Lastname"
            )

            AuthTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder =  "Phone"
            )

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                placeholder =  "Email",
                isError = isEmailError || isEmptyError
            )

            AuthTextField(
                value = username,
                onValueChange = { username = it },
                placeholder =  "Username"
            )


            // Button
            AppButton(
                onClick = {

                },
                text = "Save",
            )
        }


}