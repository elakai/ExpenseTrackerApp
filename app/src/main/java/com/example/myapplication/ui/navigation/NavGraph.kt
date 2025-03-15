// NavGraph.kt
package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.AddExpenseScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.viewmodel.ExpenseViewModel

@Composable
fun NavGraph(viewModel: ExpenseViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable("add_expense") {
            AddExpenseScreen(navController = navController, viewModel = viewModel)
        }
    }
}