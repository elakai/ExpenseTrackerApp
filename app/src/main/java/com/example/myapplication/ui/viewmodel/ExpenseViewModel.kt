package com.example.myapplication.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Expense
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpenseViewModel(context: Context) : ViewModel(), ExpenseViewModelInterface { // Implement the interface
    private val expenseDao = AppDatabase.getDatabase(context).expenseDao()

    private val _weekStart = MutableStateFlow(calculateWeekStart())

    override val expenses: Flow<List<Expense>> = expenseDao.getAllExpenses() // Override the interface property

    @OptIn(ExperimentalCoroutinesApi::class)
    override val weeklyTotal: Flow<Double?> = _weekStart.flatMapLatest { weekStart -> // Override the interface property
        expenseDao.getWeeklyTotal(weekStart)
    }

    val combinedData = combine(expenses, weeklyTotal) { expenses, total ->
        Pair(expenses, total)
    }

    override fun addExpense(name: String, amount: Double, category: String) { // Override the interface function
        viewModelScope.launch {
            val expense = Expense(name = name, amount = amount, category = category)
            expenseDao.insertExpense(expense)
        }
    }

    private fun calculateWeekStart(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}