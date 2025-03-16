package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Expense
import com.example.myapplication.ui.navigation.Screen
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.ExpenseViewModel
import com.example.myapplication.ui.viewmodel.ExpenseViewModelFactory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.net.URLEncoder

@Composable
fun ExpenseListScreen(navController: NavController, viewModel: ExpenseViewModel) {
    val allExpenses by viewModel.expenses.collectAsStateWithLifecycle(initialValue = emptyList())

    var selectedMonth by remember { mutableStateOf(getMonthString(System.currentTimeMillis())) }
    var expandedDropdown by remember { mutableStateOf(false) }

    val monthlyExpenses = remember(allExpenses) {
        processExpenses(allExpenses)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Expenses",
                    style = Typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp).weight(1f)
                )

                Box {
                    Button(onClick = { expandedDropdown = true }) {
                        Text(selectedMonth, style = Typography.bodyLarge)
                    }
                    DropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false }
                    ) {
                        val distinctMonths = monthlyExpenses.map { it.month }.distinct()
                        distinctMonths.forEach { month ->
                            DropdownMenuItem(
                                text = { Text(month, style = Typography.bodyLarge) },
                                onClick = {
                                    selectedMonth = month
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            val expensesToDisplay = if (selectedMonth == getMonthString(System.currentTimeMillis())) {
                val currentWeek = getWeekOfMonth(System.currentTimeMillis())
                val currentMonthData = monthlyExpenses.find { it.month == selectedMonth }
                currentMonthData?.weeklyData?.find { it.week == currentWeek }?.expenses ?: emptyList()
            } else {
                monthlyExpenses.find { it.month == selectedMonth }?.weeklyData?.flatMap { it.expenses } ?: emptyList()
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(expensesToDisplay) { expense ->
                    ExpenseItem(expense = expense)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    val encodedMonth = URLEncoder.encode(selectedMonth, "UTF-8")
                    navController.navigate("chart/${encodedMonth}") // Corrected Navigation
                }) {
                    Text("View Chart", style = Typography.bodyLarge)
                }
                Button(
                    onClick = { navController.navigate(Screen.AddExpense.route) },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("Add Expense", style = Typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.name, style = Typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                Text(text = dateFormat.format(expense.date), style = Typography.bodyMedium, color = Color.Gray)
            }
            Text(text = "$${expense.amount}", style = Typography.bodyLarge)
        }
    }
}

fun getMonthString(date: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    val format = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return format.format(calendar.time)
}

fun getWeekOfMonth(date: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    return calendar.get(Calendar.WEEK_OF_MONTH)
}

data class WeeklyExpenses(
    val week: Int,
    val expenses: List<Expense>,
    val weeklyTotal: Double
)

data class MonthlyExpenses(
    val month: String,
    val weeklyData: List<WeeklyExpenses>
)

fun processExpenses(expenses: List<Expense>): List<MonthlyExpenses> {
    return expenses.groupBy { getMonthString(it.date) }
        .map { (month, expensesInMonth) ->
            val weeklyData = expensesInMonth.groupBy { getWeekOfMonth(it.date) }
                .map { (week, expensesInWeek) ->
                    WeeklyExpenses(
                        week = week,
                        expenses = expensesInWeek,
                        weeklyTotal = expensesInWeek.sumOf { it.amount }
                    )
                }
            MonthlyExpenses(
                month = month,
                weeklyData = weeklyData.sortedBy { it.week }
            )
        }.sortedByDescending {
            val date = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).parse(it.month)
            date?.time ?: 0
        }
}

@Preview(showBackground = true)
@Composable
fun ExpenseListScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = ExpenseViewModelFactory(context))
    ExpenseListScreen(navController, viewModel)
}