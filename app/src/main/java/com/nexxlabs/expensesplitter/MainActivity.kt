package com.nexxlabs.expensesplitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.nexxlabs.expensesplitter.navigation.AppNavHost
import com.nexxlabs.expensesplitter.ui.settings.ThemeMode
import com.nexxlabs.expensesplitter.ui.theme.AppThemeViewModel
import com.nexxlabs.expensesplitter.ui.theme.ExpenseSplitterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: AppThemeViewModel = hiltViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()

            val darkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }
            ExpenseSplitterTheme(darkTheme = darkTheme) {
                AppNavHost()
            }
        }
    }
}
