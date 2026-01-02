package com.nexxlabs.expensesplitter.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexxlabs.expensesplitter.R

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenPrivacy: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode by viewModel.themePreferences.themeMode.collectAsState(
        initial = ThemeMode.SYSTEM
    )

    val painterImg = when(themeMode){
        ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) painterResource(R.drawable.moon) else painterResource(R.drawable.sun)
        ThemeMode.LIGHT -> painterResource(R.drawable.sun)
        ThemeMode.DARK -> painterResource(R.drawable.moon)
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {

        // Top header (same pattern as other screens)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // App Theme
            SettingsCard(
                icon = painterImg,
                iconBackground = Color(0xFFE7F0FF),
                iconTint = MaterialTheme.colorScheme.primary,
                title = "App Theme",
                subtitle = "Choose how the app looks"
            ) {
                ThemeDropdown(
                    selected = themeMode,
                    onSelected = viewModel::setTheme
                )
            }

            // Share App

            SettingsCard(
                icon = painterResource(R.drawable.share),
                iconBackground = Color(0xFFE6F8ED),
                iconTint = Color(0xFF22C55E),
                title = "Share App",
                subtitle = "Tell your friends about this app",
                onClick = { viewModel.shareApp() }
            )

            // About Us
            SettingsCard(
                icon = painterResource(R.drawable.info),
                iconBackground = Color(0xFFF3E8FF),
                iconTint = Color(0xFF8B5CF6),
                title = "About Us",
                subtitle = "Learn more about this app",
                onClick = onOpenAbout
            )

            // Privacy Policy
            SettingsCard(
                icon = painterResource(R.drawable.info),
                iconBackground = Color(0xFFFFEDD5),
                iconTint = Color(0xFFF97316),
                title = "Privacy Policy",
                subtitle = "How we handle your data",
                onClick = onOpenPrivacy
            )
        }
    }
}
