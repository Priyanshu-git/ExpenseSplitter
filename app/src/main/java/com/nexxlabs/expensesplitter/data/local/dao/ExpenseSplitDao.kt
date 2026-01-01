package com.nexxlabs.expensesplitter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nexxlabs.expensesplitter.data.local.entity.ExpenseSplitEntity

@Dao
interface ExpenseSplitDao {

    @Insert
    suspend fun insertAll(splits: List<ExpenseSplitEntity>)

    @Query("""
        SELECT memberId, SUM(shareAmount) as totalShare
        FROM expense_splits
        WHERE expenseId IN (
            SELECT id FROM expenses WHERE groupId = :groupId
        )
        GROUP BY memberId
    """)
    suspend fun getTotalSharePerMember(groupId: Long): List<MemberShare>
}

data class MemberShare(
    val memberId: Long,
    val totalShare: Double
)
