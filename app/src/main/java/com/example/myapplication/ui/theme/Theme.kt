package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.R // Replace with your R file import

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = Color(0xFF6200EA),
        secondary = Color(0xFF03DAC6)
    )

    val FreshSeasonFamily = FontFamily(
        Font(R.font.fresh_season, FontWeight.Normal) // Use R.font.fresh_season
    )

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
