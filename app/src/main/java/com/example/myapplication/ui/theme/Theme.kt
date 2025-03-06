package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = Color(0xFF6200EA),
        secondary = Color(0xFF03DAC6)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
