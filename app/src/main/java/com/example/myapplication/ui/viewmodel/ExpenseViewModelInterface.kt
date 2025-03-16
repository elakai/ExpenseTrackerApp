package com.example.myapplication.ui.viewmodel

import com.example.myapplication.data.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ExpenseViewModelInterface {
    val expenses: StateFlow<List<Expense>>
    val weeklyTotal: StateFlow<Double>
    val toBuyItems: StateFlow<List<String>>
    val weeklyExpenses: Flow<List<Expense>>
    fun addExpense(name: String, amount: Double, category: String, notes: String, dateString: String)
    fun addToBuyItem(item: String)
    fun removeToBuyItem(index: Int)
    fun getExpensesForMonth(month: String): Flow<List<Expense>> // MUST BE HERE
}