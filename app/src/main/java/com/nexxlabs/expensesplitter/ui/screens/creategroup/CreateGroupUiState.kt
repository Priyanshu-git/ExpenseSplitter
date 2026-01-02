package com.nexxlabs.expensesplitter.ui.screens.creategroup

data class CreateGroupUiState(
    val groupName: String = "",
    val currency: String = "INR",
    val memberInput: String = "",
    val members: List<String> = emptyList(),
    val error: String? = null,
    val isSaving: Boolean = false
)
