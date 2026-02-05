package com.example.bicyclerentalapp.ui.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
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
import com.example.bicyclerentalapp.model.User
import com.example.bicyclerentalapp.ui.auth.AuthError
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.auth.UserIntent
import com.example.bicyclerentalapp.ui.components.AppButton
import com.example.bicyclerentalapp.ui.components.AuthTextField
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper

@Composable
fun EditProfileScreen(viewModel: AuthViewModel, onClick: () -> Unit){
    val user by viewModel.logInResult.collectAsState()

    val error by viewModel.errorState.collectAsState()
    val isEmailError = error == AuthError.InvalidEmail
    val isEmptyError = error == AuthError.EmptyFields

    var username by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            username = it.username
            firstname = it.firstname
            lastname = it.lastname
            phone = it.phone
            email = it.email
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

        // button
        AppButton(
            onClick = {
                val user = User(
                    idUser = user?.idUser ?: 0,
                    username = username,
                    password = user?.password ?: "",
                    firstname = firstname,
                    lastname = lastname,
                    phone = phone,
                    email = email
                )
                viewModel.processIntent(UserIntent.EditProfile(user))
                onClick()
            },
            text = "Save",
        )
    }
}