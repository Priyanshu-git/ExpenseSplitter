package com.nexxlabs.expensesplitter.ui.creategroup

data class CreateGroupUiState(
    val groupName: String = "",
    val members: List<String> = listOf(""),
    val isSaving: Boolean = false,
    val error: String? = null
)
