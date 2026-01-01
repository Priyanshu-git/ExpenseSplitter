package com.nexxlabs.expensesplitter.ui.groupdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseDao
import com.nexxlabs.expensesplitter.data.local.dao.GroupDao
import com.nexxlabs.expensesplitter.data.local.dao.MemberDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    groupDao: GroupDao,
    memberDao: MemberDao,
    expenseDao: ExpenseDao
) : ViewModel() {

    private val groupId: Long =
        savedStateHandle.get<String>("groupId")!!.toLong()

    val groupIdValue: Long get() = groupId


    val uiState: StateFlow<GroupDetailUiState> =
        combine(
            groupDao.observeGroups(),
            memberDao.observeMembers(groupId),
            expenseDao.observeExpenses(groupId)
        ) { groups, members, expenses ->

            val group = groups.first { it.id == groupId }

            GroupDetailUiState(
                groupName = group.name,
                members = members.map { it.name },
                expenses = expenses.map { expense ->
                    val payer =
                        members.firstOrNull { it.id == expense.paidByMemberId }?.name
                            ?: "Unknown"

                    ExpenseItem(
                        id = expense.id,
                        title = expense.title,
                        amount = "₹${expense.amount.toInt()}",
                        paidBy = payer
                    )
                },
                isEmpty = expenses.isEmpty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GroupDetailUiState()
        )
}
