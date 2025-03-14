package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses WHERE date >= :weekStart")
    fun getWeeklyTotal(weekStart: Long): Flow<Double?>

    @Insert
    suspend fun insertExpense(expense: Expense)
}
