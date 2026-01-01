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
        groupRepository.observeGroups()
            .map { groups ->
                val items = groups.map {
                    GroupItem(
                        id = it.id,
                        name = it.name,
                        totalExpense = "₹0" // will be calculated later
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
