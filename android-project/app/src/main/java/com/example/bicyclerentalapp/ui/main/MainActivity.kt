package com.example.bicyclerentalapp.ui.main

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bicyclerentalapp.ui.theme.BicycleRentalAppTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContent {
            BicycleRentalAppTheme {
                BicycleRentalMainScreen()
            }
        }
    }
}