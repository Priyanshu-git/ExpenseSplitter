package com.nexxlabs.expensesplitter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MemberChipsRow(
    members: List<String>,
    onRemove: (String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        members.forEach { name ->
            MemberChip(
                name = name,
                onRemove = { onRemove(name) }
            )
        }
    }
}
