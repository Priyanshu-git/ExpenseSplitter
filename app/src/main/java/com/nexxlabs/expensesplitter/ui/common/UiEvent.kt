package com.nexxlabs.expensesplitter.ui.common

import android.content.Intent

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
    data class StartIntent(val intent: Intent, val title: String) : UiEvent
}
