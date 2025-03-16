package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT SUM(amount) FROM expenses WHERE date >= :weekStart")
    fun getWeeklyTotal(weekStart: Long): Flow<Double?>

    @Query("SELECT * FROM expenses WHERE date >= date('now', 'weekday 0', '-7 days') ORDER BY date DESC")
    fun getWeeklyExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getExpensesBetween(startDate: Long, endDate: Long): Flow<List<Expense>>
}