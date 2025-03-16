package com.example.myapplication.ui.viewmodel

import com.example.myapplication.data.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class PreviewExpenseViewModel : ExpenseViewModelInterface {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    override val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _weeklyTotal = MutableStateFlow(0.0)
    override val weeklyTotal: StateFlow<Double> = _weeklyTotal.asStateFlow()

    private val _weeklyExpenses = MutableStateFlow<List<Expense>>(emptyList())
    override val weeklyExpenses: Flow<List<Expense>> = _weeklyExpenses

    private val _toBuyItems = MutableStateFlow<List<String>>(emptyList())
    override val toBuyItems: StateFlow<List<String>> = _toBuyItems.asStateFlow()

    override fun addExpense(name: String, amount: Double, category: String, notes: String, dateString: String) {
        println("Preview: Adding expense - $name, $amount, $category, $notes, $dateString")
    }

    override fun addToBuyItem(item: String) {
        println("Preview: Adding to buy item - $item")
    }

    override fun removeToBuyItem(index: Int) {
        println("Preview: Removing to buy item at index - $index")
    }

    override fun getExpensesForMonth(month: String): Flow<List<Expense>> {
        // Dummy implementation for previews
        return flowOf(listOf(
            Expense(name = "Preview Expense", amount = 25.0, category = "Preview", date = System.currentTimeMillis(), notes = "")
        ))
    }
}