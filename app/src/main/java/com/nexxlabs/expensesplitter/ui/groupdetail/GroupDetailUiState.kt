package com.nexxlabs.expensesplitter.ui.groupdetail

data class GroupDetailUiState(
    val groupName: String = "",
    val members: List<String> = emptyList(),
    val expenses: List<ExpenseItem> = emptyList(),
    val isEmpty: Boolean = true
)

data class ExpenseItem(
    val id: Long,
    val title: String,
    val amount: String,
    val paidBy: String
)
