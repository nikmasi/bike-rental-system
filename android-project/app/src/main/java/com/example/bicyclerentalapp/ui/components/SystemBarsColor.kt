package com.example.bicyclerentalapp.ui.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

@Composable
fun SystemBarsColor(
    color: androidx.compose.ui.graphics.Color,
    darkTheme: Boolean
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = color.toArgb()
            window.navigationBarColor = color.toArgb()

            ViewCompat.getWindowInsetsController(view)?.apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }
}
