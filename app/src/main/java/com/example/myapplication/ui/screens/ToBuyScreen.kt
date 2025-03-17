package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R // Import your R file
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.viewmodel.ExpenseViewModelInterface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToBuyScreen(navController: NavController, viewModel: ExpenseViewModelInterface) {
    var newItem by remember { mutableStateOf("") }
    val toBuyItems by viewModel.toBuyItems.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg2),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("To Buy List", style = Typography.headlineLarge, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItem,
                    onValueChange = { newItem = it },
                    label = { Text("Add Item", style = Typography.bodyLarge, color = Color.Black) },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (newItem.isNotEmpty()) {
                        viewModel.addToBuyItem(newItem)
                        newItem = ""
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFead6f9)) ) {
                    Text("Add", style = Typography.bodyLarge, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                toBuyItems.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item, style = Typography.titleMedium, modifier = Modifier.weight(1f), color = Color.Black)
                        IconButton(onClick = { viewModel.removeToBuyItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Black)
                        }
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