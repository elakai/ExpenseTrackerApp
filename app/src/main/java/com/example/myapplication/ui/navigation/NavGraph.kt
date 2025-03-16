package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.viewmodel.ExpenseViewModel
import com.example.myapplication.ui.viewmodel.ExpenseViewModelFactory
import java.net.URLDecoder

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: ExpenseViewModel = viewModel(factory = ExpenseViewModelFactory(context))

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.ExpenseList.route) {
            ExpenseListScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.AddExpense.route) {
            AddExpenseScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.Chart.route) { backStackEntry ->
            val encodedMonth = backStackEntry.arguments?.getString("selectedMonth")
            val selectedMonth = encodedMonth?.let { URLDecoder.decode(it, "UTF-8") }
            ChartScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.ToBuy.route) {
            ToBuyScreen(navController = navController, viewModel = viewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ExpenseList : Screen("expense_list")
    object AddExpense : Screen("add_expense")
    object ToBuy : Screen("tobuy")
    object Chart : Screen("chart/{selectedMonth}")
}