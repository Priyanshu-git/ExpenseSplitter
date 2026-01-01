package com.nexxlabs.expensesplitter.ui.screens.home

data class HomeUiState(
    val groups: List<GroupItem> = emptyList(),
    val isEmpty: Boolean = true
)

data class GroupItem(
    val id: Long,
    val name: String,
    val totalExpense: String // formatted, e.g. "₹2,450"
)
