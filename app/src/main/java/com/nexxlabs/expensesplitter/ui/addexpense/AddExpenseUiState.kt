package com.nexxlabs.expensesplitter.ui.addexpense

data class AddExpenseUiState(
    val title: String = "",
    val amount: String = "",
    val members: List<MemberItem> = emptyList(),
    val paidByMemberId: Long? = null,
    val error: String? = null,
    val isSaving: Boolean = false
)

data class MemberItem(
    val id: Long,
    val name: String,
    val isSelected: Boolean = true
)
