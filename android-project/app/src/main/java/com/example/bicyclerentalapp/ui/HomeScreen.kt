package com.example.bicyclerentalapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bicyclerentalapp.ui.auth.AuthViewModel
import com.example.bicyclerentalapp.ui.components.wrapper.ScreenWrapper
import com.example.bicyclerentalapp.ui.rental.BikeCard
import com.example.bicyclerentalapp.ui.theme.BicycleRentalAppTheme

@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    onBikeClick: (Int) -> Unit
) {
    val user by viewModel.logInResult.collectAsState()

    ScreenWrapper(title = "Welcome, ${user?.firstname ?: "Rider"}!") {
        // 1. Filteri (Horizontalni list)
        CategoryTabs()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Available near you",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // 3. Lista bicikala
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(10) { index -> // Simulacija podataka
                BikeCard(
                    name = "Mountain Pro $index",
                    price = "5€/hr",
                    type = "Mountain",
                    onClick = { onBikeClick(index) }
                )
            }
        }
    }
}

@Composable
fun BikeCard(
    name: String,
    price: String,
    type: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF1E1E1E), // Malo svetlija od BackgroundBlack da se vidi dubina
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder za sliku bicikla
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF2C2C2C),
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Informacije o biciklu
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = type,
                    color = Color.Cyan.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = price,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }

            // Dugme "Strelica" za detalje
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
@Composable
fun CategoryTabs() {
    val categories = listOf("All", "Mountain", "City", "Electric", "Road")
    var selectedCategory by remember { mutableStateOf("All") }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category
            Surface(
                onClick = { selectedCategory = category },
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) Color.White else Color(0xFF252525),
                modifier = Modifier.height(40.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.Black else Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    BicycleRentalAppTheme {
        //HomeScreen()
    }
}