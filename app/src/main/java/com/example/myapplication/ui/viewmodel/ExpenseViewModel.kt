package com.example.myapplication.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Expense
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class MonthlyExpenses(
    val month: String,
    val weeklyData: List<WeeklyExpenses>
)

data class WeeklyExpenses(
    val week: Int,
    val expenses: List<Expense>,
    val weeklyTotal: Double
)

class ExpenseViewModel(context: Context) : ViewModel(), ExpenseViewModelInterface {
    private val expenseDao = AppDatabase.getDatabase(context).expenseDao()
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    override val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()
    private val _weeklyTotal = MutableStateFlow<Double>(0.0)
    override val weeklyTotal: StateFlow<Double> = _weeklyTotal.asStateFlow()
    private val _monthlyExpenses = MutableStateFlow<List<MonthlyExpenses>>(emptyList())
    val monthlyExpenses: StateFlow<List<MonthlyExpenses>> = _monthlyExpenses.asStateFlow()
    private val _toBuyItems = MutableStateFlow<List<String>>(emptyList())
    override val toBuyItems: StateFlow<List<String>> = _toBuyItems.asStateFlow()

    init {
        viewModelScope.launch {
            expenseDao.getAllExpenses().collect { allExpenses ->
                _expenses.value = allExpenses
                _monthlyExpenses.value = processExpenses(allExpenses)
            }
        }
        viewModelScope.launch {
            expenseDao.getWeeklyTotal(getWeekStart()).collect {
                _weeklyTotal.value = it ?: 0.0
            }
        }
    }

    override fun addExpense(
        name: String,
        amount: Double,
        category: String,
        notes: String,
        dateString: String
    ) {
        viewModelScope.launch {
            val date = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(dateString)?.time
                ?: System.currentTimeMillis()
            expenseDao.insertExpense(
                Expense(
                    name = name,
                    amount = amount,
                    category = category,
                    date = date,
                    notes = notes
                )
            )
        }
    }

    fun deleteExpense(expense: Expense) { // Added deleteExpense function
        viewModelScope.launch {
            expenseDao.deleteExpense(expense)
        }
    }

    override fun addToBuyItem(item: String) {
        _toBuyItems.value += item
    }

    override fun removeToBuyItem(index: Int) {
        _toBuyItems.value = _toBuyItems.value.filterIndexed { i, _ -> i != index }
    }

    private fun getWeekStart(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun processExpenses(expenses: List<Expense>): List<MonthlyExpenses> {
        return expenses.groupBy { getMonthString(it.date) }
            .map { (month, expensesInMonth) ->
                val weeklyData = expensesInMonth.groupBy { getWeekOfMonth(it.date) }
                    .map { (week, expensesInWeek) ->
                        WeeklyExpenses(
                            week = week,
                            expenses = expensesInWeek,
                            weeklyTotal = expensesInWeek.sumOf { it.amount }
                        )
                    }
                MonthlyExpenses(
                    month = month,
                    weeklyData = weeklyData.sortedBy { it.week }
                )
            }.sortedByDescending {
                val date = SimpleDateFormat("MMMM पुरालेखित", Locale.getDefault()).parse(it.month)
                date?.time ?: 0
            }
    }

    private fun getWeekOfMonth(date: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        return calendar.get(Calendar.WEEK_OF_MONTH)
    }

    private fun getMonthString(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val format = SimpleDateFormat("MMMM पुरालेखित", Locale.getDefault())
        return format.format(calendar.time)
    }

    private fun getCurrentDay(): Long {
        return Calendar.getInstance().timeInMillis
    }

    private fun getSevenDaysAgo(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        return calendar.timeInMillis
    }

    override val weeklyExpenses: Flow<List<Expense>> =
        expenseDao.getExpensesBetween(getSevenDaysAgo(), getCurrentDay()).onEach { expenses ->
            Log.d("ExpenseViewModel", "Weekly Expenses (Explicit Range): $expenses")
            if (expenses.isEmpty()) {
                Log.d("ExpenseViewModel", "  (No expenses in this week)")
            } else {
                expenses.forEach {
                    Log.d(
                        "ExpenseViewModel",
                        "  ${it.name} - ${it.amount} - ${it.date} - ${it.category}"
                    )
                }
            }
        }

    override fun getExpensesForMonth(month: String): Flow<List<Expense>> {
        return _expenses.map { expenses ->
            expenses.filter { getMonthString(it.date) == month }
        }
    }
}