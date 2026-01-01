package com.nexxlabs.expensesplitter.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.preferences.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val themePreferences: ThemePreferences
) : ViewModel() {

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            themePreferences.setTheme(mode)
        }
    }
}
