package com.nexxlabs.expensesplitter.ui.screens.summary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseDao
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseSplitDao
import com.nexxlabs.expensesplitter.data.local.dao.MemberDao
import com.nexxlabs.expensesplitter.data.repository.SettlementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val memberDao: MemberDao,
    private val expenseDao: ExpenseDao,
    private val splitDao: ExpenseSplitDao,
    private val settlementRepository: SettlementRepository
) : ViewModel() {

    private val groupId: Long =
        savedStateHandle.get<String>("groupId")!!.toLong()

    val uiState: StateFlow<SummaryUiState> =
        combine(
            memberDao.observeMembers(groupId),
            expenseDao.observeExpenses(groupId)
        ) { members, expenses ->

            val paidMap = mutableMapOf<Long, Double>()
            expenses.forEach {
                paidMap[it.paidByMemberId] =
                    (paidMap[it.paidByMemberId] ?: 0.0) + it.amount
            }

            val shareMap = mutableMapOf<Long, Double>()
            splitDao.getTotalSharePerMember(groupId).forEach {
                shareMap[it.memberId] = it.totalShare
            }

            val balances = members.map { member ->
                val paid = paidMap[member.id] ?: 0.0
                val share = shareMap[member.id] ?: 0.0
                val balance = paid - share

                MemberBalanceItem(
                    name = member.name,
                    balance = balance
                )
            }

            val settlements = settlementRepository
                .calculateSettlements(
                    balances.map {
                        com.nexxlabs.expensesplitter.data.repository.MemberBalance(
                            memberId = 0L,
                            name = it.name,
                            balance = it.balance
                        )
                    }
                )
                .map {
                    SettlementItem(
                        from = it.from,
                        to = it.to,
                        amount = "₹${it.amount.toInt()}"
                    )
                }

            SummaryUiState(
                balances = balances,
                settlements = settlements,
                isSettled = settlements.isEmpty()
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SummaryUiState()
            )
}
