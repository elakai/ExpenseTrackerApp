package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R // Replace with your R file import

val FreshSeasonFamily = FontFamily(
    Font(R.font.fresh_season, FontWeight.Normal)
)

val LemonTuesdayFamily = FontFamily(
    Font(R.font.lemon_tuesday, FontWeight.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FreshSeasonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = LemonTuesdayFamily, // Using Lemon Tuesday for headlines
        fontWeight = FontWeight.SemiBold, // Or FontWeight.Normal
        fontSize = 28.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = LemonTuesdayFamily, // Using Lemon Tuesday for headlines
        fontWeight = FontWeight.SemiBold, // Or FontWeight.Normal
        fontSize = 38.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FreshSeasonFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
)