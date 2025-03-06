package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.Expense
import com.example.myapplication.ui.viewmodel.ExpenseViewModel

@Composable
fun AddExpenseScreen(navController: NavController, viewModel: ExpenseViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add Expense", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Expense Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Expense Type") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (name.isNotEmpty() && type.isNotEmpty() && amount.isNotEmpty()) {
                viewModel.addExpense(Expense(name = name, type = type, amount = amount.toDouble()))
                navController.popBackStack() // 🔥 Fixed navigation
            }
        }) {
            Text("Save Expense")
        }
    }
}
