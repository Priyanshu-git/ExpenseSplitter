package com.nexxlabs.expensesplitter.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.preferences.ThemePreferences
import com.nexxlabs.expensesplitter.ui.screens.settings.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    themePreferences: ThemePreferences
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> =
        themePreferences.themeMode
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ThemeMode.SYSTEM
            )
}
