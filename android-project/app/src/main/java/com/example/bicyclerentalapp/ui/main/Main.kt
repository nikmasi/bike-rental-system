package com.example.bicyclerentalapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bicyclerentalapp.ui.MapScreen
import com.example.bicyclerentalapp.ui.profile.ProfileScreen
import com.example.bicyclerentalapp.ui.auth.LoginScreen
import com.example.bicyclerentalapp.ui.SplashScreen
import com.example.bicyclerentalapp.ui.WelcomeScreen
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.auth.SignUpScreen
import com.example.bicyclerentalapp.ui.components.bars.BicycleBottomBar
import androidx.compose.runtime.getValue
import com.example.bicyclerentalapp.ui.HomeScreen
import com.example.bicyclerentalapp.ui.rental.RentABikeScreen
import com.example.bicyclerentalapp.ui.profile.ChangePasswordScreen
import com.example.bicyclerentalapp.ui.rental.ActiveRentalScreen
import com.example.bicyclerentalapp.ui.rental.FinishRentalScreen
import com.example.bicyclerentalapp.ui.rental.ProblemScreen
import com.example.bicyclerentalapp.ui.rental.RentalHistoryScreen

@Composable
fun BicycleRentalMainScreen(){
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    val currentScreen = NavScreen.fromRoute(currentRoute)

    Scaffold(
        topBar = {
            // TopAppBar()
        },
        bottomBar = {
            if (currentScreen?.showBottomBar == true) {
                BicycleBottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.SplashScreen.route,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(NavScreen.SplashScreen.route){
                SplashScreen(
                    viewModel = authViewModel,
                    onNavigateToHome = {
                        navController.navigate(NavScreen.Home.route) {
                            popUpTo(NavScreen.SplashScreen.route) { inclusive = true }
                        }
                    },
                    onNavigateToWelcome = {
                        navController.navigate(NavScreen.WelcomeScreen.route) {
                            popUpTo(NavScreen.SplashScreen.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(NavScreen.WelcomeScreen.route){
                WelcomeScreen(
                    onLoginClick = {navController.navigate(NavScreen.LoginScreen.route)},
                    onSignUpClick = {navController.navigate(NavScreen.SignUp.route)}
                )
            }
            composable(NavScreen.LoginScreen.route){
                LoginScreen(
                    onLogIn = {navController.navigate(NavScreen.Home.route)},
                    viewModel = authViewModel
                )
            }
            composable(NavScreen.SignUp.route){
                SignUpScreen(
                    viewModel = authViewModel,
                    onSignUp = {navController.navigate(NavScreen.Home.route)}
                )
            }
            composable(NavScreen.Home.route){
                HomeScreen(viewModel = authViewModel, onBikeClick = {navController.navigate(
                    NavScreen.RentABikeScreen.route)})
            }
            composable(NavScreen.ProfileScreen.route){
                ProfileScreen(viewModel = authViewModel,{},
                    {navController.navigate(NavScreen.ChangePasswordScreen.route)},
                    {navController.navigate(NavScreen.RentalHistoryScreen.route)}
                )
            }
            composable(NavScreen.ChangePasswordScreen.route){
                ChangePasswordScreen(
                    onOK = { navController.navigate(NavScreen.ProfileScreen.route) }
                )
            }
            composable(NavScreen.MapScreen.route){
                MapScreen()
            }
            composable(NavScreen.RentABikeScreen.route){
                RentABikeScreen()
            }
            composable(NavScreen.ActiveRentalScreen.route){
                ActiveRentalScreen(onFinishClick = {navController.navigate(NavScreen.FinishRentalScreen.route)})
            }
            composable(NavScreen.ActiveRentalScreen.route){
                FinishRentalScreen(onFinishClick = {navController.navigate(NavScreen.ProblemScreen.route)})
            }
            composable(NavScreen.ProblemScreen.route){
                ProblemScreen()
            }
            composable(NavScreen.RentalHistoryScreen.route){
                RentalHistoryScreen({})
            }
        }
    }
}

sealed class NavScreen(
    val route: String,
    val icon: ImageVector = Icons.Filled.Title,
    val label: String = "",
    val showBottomBar: Boolean = false
){
    object WelcomeScreen: NavScreen("Welcome")
    object Home: NavScreen("Home",
        icon = Icons.Filled.Home,
        label = "Home",
        showBottomBar = true
    )
    object LoginScreen: NavScreen("Login")
    object SignUp: NavScreen("SignUp")
    object SplashScreen: NavScreen("SplashScreen")

    object ProfileScreen: NavScreen(
        route = "ProfileScreen",
        icon = Icons.Filled.Person,
        label = "Profile",
        showBottomBar = true
    )
    object ChangePasswordScreen: NavScreen(route = "ChangePasswordScreen")

    object MapScreen: NavScreen(
        route = "MapScreen",
        icon = Icons.Filled.Map,
        label = "Map",
        showBottomBar = true
    )

    object RentABikeScreen: NavScreen("RentABikeScreen", showBottomBar = true)
    object ActiveRentalScreen: NavScreen(
        "ActiveRentalScreen",
        label = "Active Rental",
        showBottomBar = true
    )

    object FinishRentalScreen: NavScreen("FinishRentalScreen")

    object ProblemScreen: NavScreen("ProblemScreen")

    object RentalHistoryScreen: NavScreen("RentalHistoryScreen")


    companion object {
        fun getBottomDestinations(): List<NavScreen> {
            return listOf(Home, MapScreen,ActiveRentalScreen, ProfileScreen)
        }

        fun fromRoute(route: String?): NavScreen? =
            getBottomDestinations().find { it.route == route }
                ?: listOf(
                    SplashScreen,
                    WelcomeScreen,
                    LoginScreen,
                    RentABikeScreen,
                    RentalHistoryScreen,
                    SignUp,
                    ActiveRentalScreen
                ).find { it.route == route }
    }
}