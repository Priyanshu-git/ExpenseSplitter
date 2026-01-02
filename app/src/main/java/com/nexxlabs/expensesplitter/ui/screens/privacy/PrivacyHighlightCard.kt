package com.nexxlabs.expensesplitter.ui.screens.privacy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrivacyHighlightCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F2E2A)
        ),
        border = BorderStroke(1.dp, Color(0xFF1DB954))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Your Privacy is Protected",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF3CE3A3)
            )
            Text(
                text = "This app is designed with privacy at its core. All your data stays on your device.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9FE7C9)
            )
        }
    }
}
