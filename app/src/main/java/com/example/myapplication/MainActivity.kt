// MainActivity.kt
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.ExpenseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: ExpenseViewModel = viewModel() // Create ViewModel

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        // Add other composables/routes here
                    }
                }
            }
        }
    }
}