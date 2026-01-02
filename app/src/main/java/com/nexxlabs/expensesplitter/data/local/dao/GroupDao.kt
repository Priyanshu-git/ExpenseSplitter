package com.nexxlabs.expensesplitter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nexxlabs.expensesplitter.data.local.entity.GroupEntity
import com.nexxlabs.expensesplitter.data.repository.model.GroupSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups ORDER BY createdAt DESC")
    fun observeGroups(): Flow<List<GroupEntity>>

    @Insert
    suspend fun insert(group: GroupEntity): Long

    @Query("DELETE FROM groups WHERE id = :groupId")
    suspend fun delete(groupId: Long)

    @Query("""
    SELECT 
        g.id AS groupId,
        g.name AS groupName,
        COUNT(DISTINCT m.id) AS memberCount,
        COUNT(DISTINCT e.id) AS expenseCount,
        IFNULL(SUM(e.amount), 0) AS totalExpense
    FROM groups g
    LEFT JOIN members m ON m.groupId = g.id
    LEFT JOIN expenses e ON e.groupId = g.id
    GROUP BY g.id
    ORDER BY g.createdAt DESC
""")
    fun observeGroupSummaries(): Flow<List<GroupSummary>>

}
