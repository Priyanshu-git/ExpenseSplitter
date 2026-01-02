package com.nexxlabs.expensesplitter.data.repository.model

data class GroupSummary(
    val groupId: Long,
    val groupName: String,
    val memberCount: Int,
    val expenseCount: Int,
    val totalExpense: Double
)