package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.AddExpenseScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.viewmodel.ExpenseViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: ExpenseViewModel) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController, viewModel)
        }
        composable("add_expense") {
            AddExpenseScreen(navController, viewModel) // 🔥 Fixed navController passing
        }
    }
}
