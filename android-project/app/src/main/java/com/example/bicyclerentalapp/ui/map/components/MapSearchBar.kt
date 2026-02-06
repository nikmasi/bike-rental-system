package com.example.bicyclerentalapp.ui.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun MapSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchExecute: () -> Unit,
    modifier: Modifier = Modifier)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BasicTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchExecute()
            }),
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) Text("Location", color = Color.Gray)
                innerTextField()
            }
        )
        IconButton(onClick = {onSearchExecute() }) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
        }
    }
}