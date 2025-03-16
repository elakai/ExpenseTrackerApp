package com.example.myapplication.ui.viewmodel

import com.example.myapplication.data.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar

class MockExpenseViewModel : ExpenseViewModelInterface {
    private val _expenses = MutableStateFlow<List<Expense>>(
        listOf(
            Expense(name = "Groceries", amount = 50.0, category = "Food", date = getMillisForDate(2023, 11, 15)),
            Expense(name = "Gas", amount = 30.0, category = "Transportation", date = getMillisForDate(2023, 11, 20)),
            Expense(name = "Movie", amount = 20.0, category = "Entertainment", date = getMillisForDate(2024, 0, 5)),
            Expense(name = "Dinner", amount = 40.0, category = "Food", date = getMillisForDate(2024, 0, 10))
        )
    )
    override val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _weeklyTotal = MutableStateFlow(
        listOf(
            Expense(name = "Groceries", amount = 50.0, category = "Food", date = System.currentTimeMillis()),
            Expense(name = "Gas", amount = 30.0, category = "Transportation", date = System.currentTimeMillis() - 86400000),
            Expense(name = "Movie", amount = 20.0, category = "Entertainment", date = System.currentTimeMillis() - 172800000)
        ).sumOf{it.amount}
    )
    override val weeklyTotal: StateFlow<Double> = _weeklyTotal.asStateFlow()

    private val _weeklyExpenses = MutableStateFlow(
        listOf(
            Expense(name = "Groceries", amount = 50.0, category = "Food", date = System.currentTimeMillis()),
            Expense(name = "Gas", amount = 30.0, category = "Transportation", date = System.currentTimeMillis() - 86400000),
            Expense(name = "Movie", amount = 20.0, category = "Entertainment", date = System.currentTimeMillis() - 172800000)
        )
    )
    override val weeklyExpenses: Flow<List<Expense>> = _weeklyExpenses.asStateFlow()

    private val _toBuyItems = MutableStateFlow<List<String>>(emptyList())
    override val toBuyItems: StateFlow<List<String>> = _toBuyItems.asStateFlow()

    override fun addExpense(name: String, amount: Double, category: String, notes: String, dateString: String) {
        val newExpense = Expense(name = name, amount = amount, category = category, notes = notes, date = System.currentTimeMillis())
        _expenses.value += newExpense
        _weeklyExpenses.value += newExpense
        _weeklyTotal.value = _weeklyExpenses.value.sumOf{it.amount}
    }

    override fun addToBuyItem(item: String) {
        _toBuyItems.value += item
    }

    override fun removeToBuyItem(index: Int) {
        _toBuyItems.value = _toBuyItems.value.filterIndexed { i, _ -> i != index }
    }

    override fun getExpensesForMonth(month: String): Flow<List<Expense>> {
        val calendar = Calendar.getInstance()
        val expenses = _expenses.value.filter { expense ->
            calendar.timeInMillis = expense.date
            val expenseMonth = calendar.get(Calendar.MONTH)
            val expenseYear = calendar.get(Calendar.YEAR)

            val monthParts = month.split(" ")
            if (monthParts.size == 2) {
                val targetMonthName = monthParts[0]
                val targetYear = monthParts[1].toInt()

                val targetMonth = when (targetMonthName) {
                    "January" -> 0
                    "February" -> 1
                    "March" -> 2
                    "April" -> 3
                    "May" -> 4
                    "June" -> 5
                    "July" -> 6
                    "August" -> 7
                    "September" -> 8
                    "October" -> 9
                    "November" -> 10
                    "December" -> 11
                    else -> -1 // Invalid month
                }

                if (targetMonth != -1) {
                    // Create a new Calendar instance, don't use system current time.
                    val targetCalendar = Calendar.getInstance().apply {
                        set(Calendar.YEAR, targetYear)
                        set(Calendar.MONTH, targetMonth)
                        set(Calendar.DAY_OF_MONTH, 1) // Set day to 1, to ensure month comparison.
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    val expenseCalendar = Calendar.getInstance().apply {
                        timeInMillis = expense.date
                        set(Calendar.DAY_OF_MONTH, 1) // Set day to 1, to ensure month comparison.
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    return@filter expenseCalendar.timeInMillis == targetCalendar.timeInMillis
                } else {
                    return@filter false // Invalid month
                }
            } else {
                return@filter false // Invalid month format
            }
        }
        return flowOf(expenses)
    }

    private fun getMillisForDate(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }
}