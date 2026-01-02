package com.nexxlabs.expensesplitter.ui.common

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
}
