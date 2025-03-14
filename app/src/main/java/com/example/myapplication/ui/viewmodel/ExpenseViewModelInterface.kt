package com.example.myapplication.ui.viewmodel

import com.example.myapplication.data.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseViewModelInterface {
    val expenses: Flow<List<Expense>>
    val weeklyTotal: Flow<Double?>
    fun addExpense(name: String, amount: Double, category: String)
}