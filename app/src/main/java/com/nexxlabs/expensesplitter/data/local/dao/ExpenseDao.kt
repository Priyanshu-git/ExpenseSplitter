package com.nexxlabs.expensesplitter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nexxlabs.expensesplitter.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE groupId = :groupId ORDER BY createdAt DESC")
    fun observeExpenses(groupId: Long): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insert(expense: ExpenseEntity): Long

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun delete(expenseId: Long)

    @Query("SELECT COUNT(*) FROM expenses WHERE groupId = :groupId")
    suspend fun getExpenseCount(groupId: Long): Int

    @Query("SELECT IFNULL(SUM(amount), 0) FROM expenses WHERE groupId = :groupId")
    suspend fun getTotalExpense(groupId: Long): Double

}
