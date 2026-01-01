package com.nexxlabs.expensesplitter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nexxlabs.expensesplitter.data.local.entity.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Query("SELECT * FROM members WHERE groupId = :groupId")
    fun observeMembers(groupId: Long): Flow<List<MemberEntity>>

    @Insert
    suspend fun insertAll(members: List<MemberEntity>)
}
