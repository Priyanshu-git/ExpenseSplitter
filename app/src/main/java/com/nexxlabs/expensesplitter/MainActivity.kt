package com.nexxlabs.expensesplitter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.nexxlabs.expensesplitter.navigation.AppNavHost
import com.nexxlabs.expensesplitter.ui.common.UiEvent
import com.nexxlabs.expensesplitter.ui.common.UiEventDispatcher
import com.nexxlabs.expensesplitter.ui.screens.settings.ThemeMode
import com.nexxlabs.expensesplitter.ui.theme.AppThemeViewModel
import com.nexxlabs.expensesplitter.ui.theme.ExpenseSplitterTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var uiEventDispatcher: UiEventDispatcher

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

            LaunchedEffect(Unit) {
                uiEventDispatcher.events.collect { event ->
                    when (event) {
                        is UiEvent.ShowToast -> {
                            Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is UiEvent.StartIntent -> {
                            startActivity(Intent.createChooser(event.intent, event.title))
                        }
                    }
                }
            }

            ExpenseSplitterTheme(darkTheme = darkTheme) {
                AppNavHost()
            }
        }
    }
}
