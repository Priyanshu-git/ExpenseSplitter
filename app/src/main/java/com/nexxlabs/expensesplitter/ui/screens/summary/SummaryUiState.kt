package com.nexxlabs.expensesplitter.ui.screens.summary

data class SummaryUiState(
    val balances: List<MemberBalanceItem> = emptyList(),
    val settlements: List<SettlementItem> = emptyList(),
    val isSettled: Boolean = true
)

data class MemberBalanceItem(
    val name: String,
    val balance: Double
)

data class SettlementItem(
    val from: String,
    val to: String,
    val amount: String
)
