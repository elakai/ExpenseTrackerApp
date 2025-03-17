package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Expense
import com.example.myapplication.ui.navigation.Screen
import com.example.myapplication.ui.viewmodel.ExpenseViewModel
import com.example.myapplication.ui.viewmodel.ExpenseViewModelFactory
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.net.URLDecoder
import java.net.URLEncoder
import androidx.compose.ui.text.TextStyle
import com.example.myapplication.ui.theme.Typography
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(navController: NavController, viewModel: ExpenseViewModel) {
    val encodedMonth = navController.currentBackStackEntry?.arguments?.getString("selectedMonth")
    val selectedMonth = encodedMonth?.let { URLDecoder.decode(it, "UTF-8") }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Monthly Expenses Chart",
                            style = Typography.headlineMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (selectedMonth != null) {
                val monthlyExpenses by viewModel.getExpensesForMonth(selectedMonth)
                    .collectAsStateWithLifecycle(initialValue = emptyList())
                ChartContent(
                    monthlyExpenses = monthlyExpenses,
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                Text("Error: Month not found.")
            }
        }

}

@Composable
fun ChartContent(monthlyExpenses: List<Expense>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val freshSeasonTypeface = ResourcesCompat.getFont(context, R.font.fresh_season)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg5),
                contentScale = ContentScale.Crop
            )
    ) {
        AndroidView(
            factory = { context ->
                PieChart(context).apply {
                    description.isEnabled = false
                    isDrawHoleEnabled = true
                    setHoleColor(android.graphics.Color.WHITE)
                    holeRadius = 40f
                    transparentCircleRadius = 50f
                    setUsePercentValues(true)
                    setDrawEntryLabels(true)
                    legend.isEnabled = true
                    setEntryLabelColor(android.graphics.Color.BLACK)
                }
            },
            update = { pieChart ->
                val categoryTotals =
                    monthlyExpenses.fold(mutableMapOf<String, Double>()) { acc, expense ->
                        val currentTotal = acc.getOrDefault(expense.category, 0.0)
                        acc[expense.category] = currentTotal + expense.amount
                        acc
                    }

                val totalExpenses: Double = if (categoryTotals.values.isNotEmpty()) {
                    categoryTotals.values.sumOf { it }
                } else {
                    0.0
                }

                val entries = categoryTotals.map { (category, total) ->
                    val percentage = if (totalExpenses > 0) {
                        (total.toFloat() / totalExpenses.toFloat() * 100f)
                    } else {
                        0f
                    }
                    PieEntry(percentage, category)
                }

                if (entries.isNotEmpty()) {
                    val dataSet = PieDataSet(entries, "")
                    dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
                    dataSet.valueTextColor = android.graphics.Color.BLACK
                    dataSet.sliceSpace = 3f

                    dataSet.valueTextSize = 25f

                    val data = PieData(dataSet)
                    pieChart.data = data

                    pieChart.setEntryLabelTextSize(15f)
                    pieChart.legend.textSize = 20f
                    pieChart.legend.textColor = android.graphics.Color.WHITE
                    if (freshSeasonTypeface != null) {
                        pieChart.setEntryLabelTypeface(freshSeasonTypeface)
                        pieChart.legend.typeface = freshSeasonTypeface
                    }

                    pieChart.invalidate()
                } else {
                    pieChart.clear()
                    pieChart.invalidate()
                }
            },
            modifier = modifier.fillMaxSize()

        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChartScreenPreview() {
    val context = LocalContext.current
    val viewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = ExpenseViewModelFactory(context))
    val navController = rememberNavController()
    val encodedMonth = URLEncoder.encode("January 2024", "UTF-8")
    navController.navigate(Screen.Chart.route + "/${encodedMonth}")
    ChartScreen(navController, viewModel)
}