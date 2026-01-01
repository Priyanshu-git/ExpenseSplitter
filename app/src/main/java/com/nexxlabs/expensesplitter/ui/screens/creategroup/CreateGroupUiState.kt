package com.nexxlabs.expensesplitter.ui.screens.creategroup

data class CreateGroupUiState(
    val groupName: String = "",
    val members: List<String> = listOf(""),
    val isSaving: Boolean = false,
    val error: String? = null
)
