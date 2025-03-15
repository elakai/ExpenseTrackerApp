package com.example.myapplication.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Expense
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.ExpenseViewModelInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(navController: NavController, viewModel: ExpenseViewModelInterface) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("Food") }
    val categories = listOf("Food", "School", "Transportation", "Personal", "Other")
    var buttonScale by remember { mutableFloatStateOf(1f) }
    var date by remember { mutableStateOf("") } // Date input field
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add Expense",
                style = Typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(2.dp)
                    .background(Color(0xFF00008B))
            )

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Expense Name", style = Typography.bodyLarge) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = Typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount", style = Typography.bodyLarge) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = Typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField( // Add Date Input
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (MM/DD/YYYY)", style = Typography.bodyLarge) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = Typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth().clickable { expanded = true },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = category,
                        onValueChange = { },
                        label = { Text("Category", style = Typography.bodyLarge) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledBorderColor = MaterialTheme.colorScheme.outline,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        textStyle = Typography.bodyLarge
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption, style = Typography.bodyLarge) },
                                onClick = {
                                    category = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    val animatedScale: Float by animateFloatAsState(
                        targetValue = buttonScale,
                        animationSpec = tween(durationMillis = 100)
                    )

                    Button(
                        onClick = {
                            buttonScale = 0.9f
                            viewModel.addExpense(name, amount.toDoubleOrNull() ?: 0.0, category)
                            scope.launch {
                                snackbarHostState.showSnackbar("Expense Added Successfully!")
                            }
                            navController.popBackStack()
                            buttonScale = 1f
                        },
                        modifier = Modifier.scale(animatedScale)
                    ) {
                        Text("Add Expense", style = Typography.bodyLarge)
                    }
                }
            }
        }
    }
}

class PreviewExpenseViewModel : ExpenseViewModelInterface {
    override val expenses: Flow<List<Expense>> = flowOf(emptyList())
    override val weeklyTotal: Flow<Double?> = flowOf(0.0)

    override fun addExpense(name: String, amount: Double, category: String) {
        println("Preview: Adding expense - $name, $amount, $category")
    }
}

@Preview(showBackground = true)
@Composable
fun AddExpenseScreenPreview() {
    MaterialTheme(typography = Typography) {
        AddExpenseScreen(rememberNavController(), PreviewExpenseViewModel())
    }
}