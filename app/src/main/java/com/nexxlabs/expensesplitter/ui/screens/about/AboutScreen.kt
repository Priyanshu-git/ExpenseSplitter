package com.nexxlabs.expensesplitter.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nexxlabs.expensesplitter.R

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Top Header
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
                    text = "About Us",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // App Icon
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.hand_coins),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Text(
                text = "Expense Splitter",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Offline Edition",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // About section
            AboutCard(title = "About This App") {
                Text(
                    text = "Expense Splitter is a simple, fully offline application that helps you split group expenses and understand who owes whom. Perfect for trips, shared living, or any group spending.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Features
            AboutCard(title = "Key Features") {
                FeatureItem("100% offline – no internet required")
                FeatureItem("Create and manage multiple groups")
                FeatureItem("Track expenses and split them equally")
                FeatureItem("See clear settlements – who owes whom")
                FeatureItem("Complete privacy – all data stays on your device")
            }

            // Developer
            AboutCard(title = "Developer") {
                Text(
                    text = "Made with ❤️ for simple expense tracking",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Version Info
            AboutCard(title = "Version Information") {
                VersionRow(label = "Version Name", value = "1.0.0")
                VersionRow(label = "Version Code", value = "1")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
