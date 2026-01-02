package com.nexxlabs.expensesplitter.ui.screens.settings

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.preferences.ThemePreferences
import com.nexxlabs.expensesplitter.ui.common.UiEvent
import com.nexxlabs.expensesplitter.ui.common.UiEventDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val themePreferences: ThemePreferences,
    val uiEventDispatcher: UiEventDispatcher
) : ViewModel() {

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            themePreferences.setTheme(mode)
        }
    }

    fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out Expense Splitter:\nhttps://play.google.com/store/apps/details?id=com.nexxlabs.expensesplitter"
            )
        }
        viewModelScope.launch {
            uiEventDispatcher.emit(UiEvent.StartIntent(intent, "Share App"))
        }


    }
}
