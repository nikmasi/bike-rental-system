package com.example.bicyclerentalapp.ui.components.bars

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bicyclerentalapp.ui.main.NavScreen

@Composable
fun BicycleBottomBar(navController: NavController){
    val startDestination = NavScreen.Home

    val destinations = NavScreen.getBottomDestinations()
    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value?.destination?.route

    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(NavScreen.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.route,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(text = destination.label, style = MaterialTheme.typography.labelSmall)
                },
            )
        }
    }
}