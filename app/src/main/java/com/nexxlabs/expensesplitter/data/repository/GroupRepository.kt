package com.nexxlabs.expensesplitter.data.repository

import com.nexxlabs.expensesplitter.data.local.dao.GroupDao
import com.nexxlabs.expensesplitter.data.local.dao.MemberDao
import com.nexxlabs.expensesplitter.data.local.entity.GroupEntity
import com.nexxlabs.expensesplitter.data.local.entity.MemberEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val groupDao: GroupDao,
    private val memberDao: MemberDao
) {

    fun observeGroups(): Flow<List<GroupEntity>> =
        groupDao.observeGroups()

    suspend fun createGroup(
        name: String,
        currency: String,
        memberNames: List<String>
    ): Long {
        val groupId = groupDao.insert(
            GroupEntity(name = name, currency = currency)
        )

        val members = memberNames.map {
            MemberEntity(
                groupId = groupId,
                name = it
            )
        }

        memberDao.insertAll(members)
        return groupId
    }

    suspend fun deleteGroup(groupId: Long) {
        groupDao.delete(groupId)
        // Cascade handles everything else
    }
}
