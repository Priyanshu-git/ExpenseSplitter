package com.nexxlabs.expensesplitter.ui.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexxlabs.expensesplitter.ui.theme.NegativeRed
import com.nexxlabs.expensesplitter.ui.theme.PositiveGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    onBack: () -> Unit,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Summary") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    "Balances",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(state.balances) { item ->
                BalanceRow(item)
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    "Settlements",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (state.isSettled) {
                item {
                    Text("🎉 All settled up!")
                }
            } else {
                items(state.settlements) { settlement ->
                    SettlementRow(settlement)
                }
            }
        }
    }
}

@Composable
private fun BalanceRow(item: MemberBalanceItem) {
    val color = when {
        item.balance > 0 -> PositiveGreen
        item.balance < 0 -> NegativeRed
        else -> MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(item.name)
        Text(
            text = "₹${item.balance.toInt()}",
            color = color
        )
    }
}

@Composable
private fun SettlementRow(item: SettlementItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Text(
            text = "${item.from} owes ${item.to} ${item.amount}",
            modifier = Modifier.padding(16.dp)
        )
    }
}


