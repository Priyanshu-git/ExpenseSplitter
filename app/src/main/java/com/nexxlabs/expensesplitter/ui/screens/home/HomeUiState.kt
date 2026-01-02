package com.nexxlabs.expensesplitter.ui.screens.home

data class HomeUiState(
    val groups: List<GroupItem> = emptyList(),
    val isEmpty: Boolean = true
)

data class GroupItem(
    val id: Long,
    val name: String,
    val memberCount: Int,
    val expenseCount: Int,
    val totalExpense: String
)
