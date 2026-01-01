package com.nexxlabs.expensesplitter.data.repository

import com.nexxlabs.expensesplitter.data.local.dao.ExpenseDao
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseSplitDao
import com.nexxlabs.expensesplitter.data.local.entity.ExpenseEntity
import com.nexxlabs.expensesplitter.data.local.entity.ExpenseSplitEntity
import javax.inject.Inject
import kotlin.math.roundToLong

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val splitDao: ExpenseSplitDao
) {

    suspend fun addExpense(
        groupId: Long,
        title: String,
        amount: Double,
        paidByMemberId: Long,
        splitBetweenMemberIds: List<Long>
    ) {
        val expenseId = expenseDao.insert(
            ExpenseEntity(
                groupId = groupId,
                title = title,
                amount = amount,
                paidByMemberId = paidByMemberId
            )
        )

        val splitCount = splitBetweenMemberIds.size
        val rawShare = amount / splitCount
        val roundedShare = rawShare.roundToLong().toDouble()

        val splits = splitBetweenMemberIds.map {
            ExpenseSplitEntity(
                expenseId = expenseId,
                memberId = it,
                shareAmount = roundedShare
            )
        }

        // Rounding adjustment → payer absorbs difference
        val totalSplit = roundedShare * splitCount
        val diff = amount - totalSplit

        val adjustedSplits =
            if (diff != 0.0) {
                splits.map {
                    if (it.memberId == paidByMemberId) {
                        it.copy(shareAmount = it.shareAmount + diff)
                    } else it
                }
            } else splits

        splitDao.insertAll(adjustedSplits)
    }

    suspend fun deleteExpense(expenseId: Long) {
        expenseDao.delete(expenseId)
    }
}
