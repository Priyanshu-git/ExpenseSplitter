package com.nexxlabs.expensesplitter.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreateGroup: () -> Unit,
    onOpenGroup: (Long) -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expense Splitter") },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateGroup) {
                Icon(Icons.Default.Add, contentDescription = "Create Group")
            }
        }
    ) { padding ->

        if (state.isEmpty) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                onCreateGroup = onCreateGroup
            )
        } else {
            GroupList(
                groups = state.groups,
                onGroupClick = onOpenGroup,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }
    }
}

@Composable
private fun GroupList(
    groups: List<GroupItem>,
    onGroupClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(groups) { group ->
            GroupCard(group, onGroupClick)
        }
    }
}

@Composable
private fun GroupCard(
    group: GroupItem,
    onClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(group.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total: ${group.totalExpense}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier,
    onCreateGroup: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "No groups yet",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Create a group to start splitting expenses",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = onCreateGroup) {
                Text("Create Group")
            }
        }
    }
}
