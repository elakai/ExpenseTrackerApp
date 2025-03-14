package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.viewmodel.ExpenseViewModel
import com.example.myapplication.data.Expense
import java.text.NumberFormat
import java.util.Locale
import com.example.myapplication.ui.theme.Typography

@Composable
fun HomeScreen(navController: NavController, viewModel: ExpenseViewModel) {
    val expenses by viewModel.expenses.collectAsState(initial = emptyList())
    val weeklyTotal by viewModel.weeklyTotal.collectAsState(initial = 0.0)
    val context = LocalContext.current
    var toBuyItems by remember { mutableStateOf(listOf("Milk", "Bread", "Eggs")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.tmp),
                contentScale = ContentScale.Crop
            )
    ) {
        // Content on top of background
        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = { navController.navigate("add_expense") }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Expense")
                    }
                },
                containerColor = Color.Transparent, // Ensure transparency for background
                contentColor = Color.White,
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    // App Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    // Weekly Summary
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Weekly Total: ${formatCurrency(weeklyTotal ?: 0.0)}",
                            style = Typography.headlineMedium,
                            color = Color.White
                        )
                        Button(onClick = {
                            Toast.makeText(context, "Weekly Summary", Toast.LENGTH_SHORT).show()
                        }) {
                            Text("Summary", style = Typography.bodyLarge)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // To Buy List
                    Text("To Buy", style = Typography.titleLarge, color = Color.White)
                    LazyColumn(modifier = Modifier.height(100.dp)) {
                        items(toBuyItems) { item ->
                            Text(item, style = Typography.bodyMedium, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Expense List
                    LazyColumn {
                        items(expenses) { expense ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(expense.name, style = Typography.titleMedium)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("${formatCurrency(expense.amount)} - ", style = Typography.bodyMedium)
                                        CategoryText(category = expense.category)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryText(category: String) {
    val context = LocalContext.current
    Text(
        text = category,
        modifier = Modifier.clickable {
            Toast.makeText(context, "Category: $category", Toast.LENGTH_SHORT).show()
        },
        style = Typography.bodyMedium
    )
}

@Composable
fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
    return format.format(amount)
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val mockExpenses = listOf(
        Expense(name = "Food", amount = 250.0, category = "Dining"),
        Expense(name = "Transport", amount = 100.0, category = "Travel"),
        Expense(name = "Entertainment", amount = 75.0, category = "Movies")
    )
    val mockWeeklyTotal = mockExpenses.sumOf { it.amount }
    HomeScreenPreview(
        expenses = mockExpenses,
        weeklyTotal = mockWeeklyTotal
    )
}

@Composable
fun HomeScreenPreview(expenses: List<Expense>, weeklyTotal: Double) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Weekly Total: ${formatCurrency(weeklyTotal)}", style = Typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(expenses) { expense ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(expense.name, style = Typography.titleMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${formatCurrency(expense.amount)} - ", style = Typography.bodyMedium)
                            CategoryText(category = expense.category)
                        }
                    }
                }
            }
        }
    }
}