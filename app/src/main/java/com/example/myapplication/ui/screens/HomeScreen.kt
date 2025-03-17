package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.viewmodel.ExpenseViewModel
import com.example.myapplication.data.Expense
import java.text.NumberFormat
import java.util.Locale
import com.example.myapplication.ui.navigation.Screen
import com.example.myapplication.ui.theme.Typography
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.ui.viewmodel.ExpenseViewModelInterface

@Composable
fun HomeScreen(navController: NavController, viewModel: ExpenseViewModelInterface) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val weeklyTotal by viewModel.weeklyTotal.collectAsStateWithLifecycle()
    val toBuyItems by viewModel.toBuyItems.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg1),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                floatingActionButton = {
                    Box {
                        FloatingActionButton(
                            onClick = { expanded = true },
                            shape = CircleShape
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Add Expense", style = Typography.bodyLarge) },
                                onClick = {
                                    navController.navigate(Screen.AddExpense.route)
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Add To Buy Item", style = Typography.bodyLarge) },
                                onClick = {
                                    navController.navigate(Screen.ToBuy.route)
                                    expanded = false
                                }
                            )
                        }
                    }
                },
                floatingActionButtonPosition = FabPosition.Center,
                containerColor = Color.Transparent,
                contentColor = Color.White,
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(Screen.ExpenseList.route) }
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFfae7ff ))
                    ) {
                        Text(
                            "Weekly Total: ${formatCurrency(weeklyTotal ?: 0.0)}",
                            style = Typography.headlineLarge,
                            color = Color.Black,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF6E6))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                "To Buy",
                                style = Typography.headlineSmall,
                                color = Color(0xFF68645c)
                            )
                            LazyColumn(Modifier.height(100.dp)) {
                                items(toBuyItems) { item ->
                                    Text(
                                        item,
                                        style = Typography.titleMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { navController.navigate(Screen.ToBuy.route) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFead6f9)) // Change button color
                            ){
                                Text("Manage List", style = Typography.bodyLarge, color = Color.Black)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        items(expenses) { expense ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .alpha(0.8f)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(expense.name, style = Typography.titleMedium)
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "${formatCurrency(expense.amount)} - ",
                                            style = Typography.bodyMedium
                                        )
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
            Toast.makeText(context, "Category: $category", Toast.LENGTH_SHORT)
                .show()
        },
        style = Typography.bodyLarge
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
    HomeScreenPreview(expenses = mockExpenses, weeklyTotal = mockWeeklyTotal)
}

@Composable
fun HomeScreenPreview(expenses: List<Expense>, weeklyTotal: Double) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAE7FF))
        ) {
            Text(
                "Weekly Total: ${formatCurrency(weeklyTotal)}",
                style = Typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(expenses) { expense ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            expense.name, style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "${formatCurrency(expense.amount)} - ",
                                style = Typography.bodyMedium
                            )
                            CategoryText(category = expense.category)
                        }
                    }
                }
            }
        }
    }
}