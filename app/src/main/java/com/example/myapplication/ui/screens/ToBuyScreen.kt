package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.ExpenseViewModelInterface // Correct import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToBuyScreen(navController: NavController, viewModel: ExpenseViewModelInterface) { // Change parameter type
    var newItem by remember { mutableStateOf("") }
    val toBuyItems by viewModel.toBuyItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("To Buy List", style = Typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Add Item", style = Typography.bodyLarge) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newItem.isNotEmpty()) {
                    viewModel.addToBuyItem(newItem)
                    newItem = ""
                }
            }) {
                Text("Add", style = Typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            toBuyItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item, style = Typography.bodyMedium, modifier = Modifier.weight(1f))
                    IconButton(onClick = { viewModel.removeToBuyItem(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToBuyScreenPreview() {
    val navController = rememberNavController()
}