package com.example.bicyclerentalapp.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bicyclerentalapp.ui.map.MapScreen
import com.example.bicyclerentalapp.ui.profile.ProfileScreen
import com.example.bicyclerentalapp.ui.auth.LoginScreen
import com.example.bicyclerentalapp.ui.SplashScreen
import com.example.bicyclerentalapp.ui.WelcomeScreen
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.auth.SignUpScreen
import com.example.bicyclerentalapp.ui.components.bars.BicycleBottomBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.bicyclerentalapp.ui.components.SystemBarsColor
import com.example.bicyclerentalapp.ui.home.HomeScreen
import com.example.bicyclerentalapp.ui.map.MapViewModel
import com.example.bicyclerentalapp.ui.map.LocationRequiredScreen
import com.example.bicyclerentalapp.ui.rental.RentABikeScreen
import com.example.bicyclerentalapp.ui.profile.ChangePasswordScreen
import com.example.bicyclerentalapp.ui.profile.EditProfileScreen
import com.example.bicyclerentalapp.ui.rental.ActiveRentalScreen
import com.example.bicyclerentalapp.ui.rental.FinishParkingRentalScreen
import com.example.bicyclerentalapp.ui.rental.FinishRentalScreen
import com.example.bicyclerentalapp.ui.rental.ProblemQuestionScreen
import com.example.bicyclerentalapp.ui.rental.ProblemScreen
import com.example.bicyclerentalapp.ui.rental.RentalHistoryScreen
import com.example.bicyclerentalapp.ui.rental.RentalViewModel

@Composable
fun BicycleRentalMainScreen(){
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val mapViewModel: MapViewModel = hiltViewModel()
    val rentalViewModel: RentalViewModel = hiltViewModel()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val currentScreen = NavScreen.fromRoute(currentRoute)
    val context = LocalContext.current

    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        )
    }

//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = {granted ->
//            hasCamPermission = granted
//        }
//    )
//    LaunchedEffect(key1 = true) {
//        launcher.launch(Manifest.permission.CAMERA)
//    }
    val permissionsToRequest = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var permissionsGranted by remember {
        mutableStateOf(
            permissionsToRequest.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionsGranted = permissions.values.all { it }
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(permissionsToRequest)
    }

    // controller for camera
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.IMAGE_ANALYSIS
            )
        }
    }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var gpsEnabled by remember { mutableStateOf(isLocationEnabled(context)) }

    SystemBarsColor(Color.Black, darkTheme = true)

    Scaffold(
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
                HomeScreen(
                    viewModel = authViewModel,
                    mapViewModel = mapViewModel,
                    onBikeClick = {navController.navigate(NavScreen.RentABikeScreen.route)}
                )
            }
            composable(NavScreen.ProfileScreen.route){
                ProfileScreen(viewModel = authViewModel, onLogout = {
                    navController.navigate(NavScreen.LoginScreen.route){
                        popUpTo(NavScreen.ProfileScreen.route) { inclusive = true }
                    }
                },
                    {navController.navigate(NavScreen.ChangePasswordScreen.route)},
                    {navController.navigate(NavScreen.EditProfileScreen.route)},
                    {navController.navigate(NavScreen.RentalHistoryScreen.route)}
                )
            }
            composable(NavScreen.ChangePasswordScreen.route){
                ChangePasswordScreen(
                    viewModel = authViewModel,
                    onOK = { navController.navigate(NavScreen.ProfileScreen.route) }
                )
            }
            composable(NavScreen.EditProfileScreen.route){
                EditProfileScreen(viewModel = authViewModel,
                    onClick = {navController.navigate(NavScreen.ProfileScreen.route)})
            }
            composable(NavScreen.MapScreen.route){
                if(gpsEnabled) {
                    MapScreen(
                        onRent = { station, isElectro ->
                            navController.navigate("RentABikeScreen/${station.idStation}/$isElectro")
                        },
                        viewModel = mapViewModel,
                        rentalViewModel = rentalViewModel
                    )
                }else {
                    LocationRequiredScreen(onRetry = {
                        gpsEnabled = isLocationEnabled(context)
                    })
                }

            }
            composable(NavScreen.RentABikeScreen.route,
                arguments = listOf(
                    navArgument("stationId") { type = NavType.IntType },
                    navArgument("isElectro") { type = NavType.BoolType }
                )
            ) { backStackEntry ->
                val stationId = backStackEntry.arguments?.getInt("stationId") ?: 0
                val isElectro = backStackEntry.arguments?.getBoolean("isElectro") ?: false

                val user by authViewModel.logInResult.collectAsState()

                RentABikeScreen(stationId, isElectro, onQrCodeScanned = { result ->
                    user?.idUser?.let { userId ->
                        Toast.makeText(context, "Code: $result", Toast.LENGTH_SHORT).show()
                        rentalViewModel.confirmRental(stationId, isElectro, result, userId)
                        navController.navigate(NavScreen.ActiveRentalScreen.route)
                    } ?: run {
                        Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show()
                    }

                }, rentalViewModel = rentalViewModel)
            }
            composable(NavScreen.ActiveRentalScreen.route){
                ActiveRentalScreen(
                    onFinishClick = {navController.navigate(NavScreen.FinishRentalScreen.route)},
                    rentalViewModel = rentalViewModel,
                    mapViewModel = mapViewModel
                )
            }
            composable(NavScreen.FinishRentalScreen.route){
                FinishRentalScreen(
                    controller = controller,
                    onFinishClick = { bitmap ->
                        capturedBitmap = bitmap
                        Toast.makeText(context, "Photo captured!", Toast.LENGTH_SHORT).show()

                        navController.navigate(NavScreen.FinishParkingRentalScreen.route)
                    },
                    context = context,
                    rentalViewModel = rentalViewModel
                )
            }
            composable(NavScreen.FinishParkingRentalScreen.route){
                FinishParkingRentalScreen(
                    bitmap = capturedBitmap,
                    onConfirm = {
                        val stationId = rentalViewModel.selectedStation.value?.idStation ?: 0

                        rentalViewModel.completeRental(
                            endStationId = stationId
                        )

                        navController.navigate(NavScreen.ProblemQuestionScreen.route)
                    },
                    rentalViewModel = rentalViewModel
                )
            }
            composable(NavScreen.ProblemQuestionScreen.route){
                ProblemQuestionScreen(
                    onConf = { navController.navigate(NavScreen.ProblemScreen.route) },
                    onDeny = { navController.navigate(NavScreen.Home.route)}
                )
            }
            composable(NavScreen.ProblemScreen.route) {
                val rental by rentalViewModel.activeRental.collectAsState()
                ProblemScreen(
                    rentalViewModel = rentalViewModel,
                    context = context,
                    controller = controller,
                    onFinishClick = { description ->

                        rentalViewModel.submitReport(rental?.idRental ?: 0,description)
                        navController.navigate(NavScreen.Home.route) {
                            popUpTo(NavScreen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(NavScreen.RentalHistoryScreen.route){
                val user by authViewModel.logInResult.collectAsState()
                rentalViewModel.allRentalsWithReports(user?.idUser ?: 0)
                RentalHistoryScreen({

                },rentalViewModel = rentalViewModel)
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

    object EditProfileScreen: NavScreen(route = "EditProfileScreen")

    object MapScreen: NavScreen(
        route = "MapScreen",
        icon = Icons.Filled.Map,
        label = "Map",
        showBottomBar = true
    )

    object RentABikeScreen: NavScreen("RentABikeScreen/{stationId}/{isElectro}", showBottomBar = true)
    object ActiveRentalScreen: NavScreen(
        "ActiveRentalScreen",
        label = "Active Rental",
        showBottomBar = true
    )

    object FinishRentalScreen: NavScreen("FinishRentalScreen")
    object FinishParkingRentalScreen: NavScreen("FinishParkingRentalScreen")
    object ProblemQuestionScreen: NavScreen("ProblemQuestionScreen")
    object ProblemScreen: NavScreen("ProblemScreen")
    object RentalHistoryScreen: NavScreen(
        "RentalHistoryScreen",
        label = "Rental History",
        showBottomBar = true
    )

    companion object {
        fun getBottomDestinations(): List<NavScreen> {
            return listOf(Home, MapScreen, RentalHistoryScreen, ProfileScreen)
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

private fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
    return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
}