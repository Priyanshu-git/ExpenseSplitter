package com.nexxlabs.expensesplitter.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    groupRepository: GroupRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        groupRepository.observeGroupSummaries()
            .map { summaries ->
                val items = summaries.map {
                    GroupItem(
                        id = it.groupId,
                        name = it.groupName,
                        memberCount = it.memberCount,
                        expenseCount = it.expenseCount,
                        totalExpense = "%.2f".format(it.totalExpense)
                    )
                }

                HomeUiState(
                    groups = items,
                    isEmpty = items.isEmpty()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState()
            )
}
