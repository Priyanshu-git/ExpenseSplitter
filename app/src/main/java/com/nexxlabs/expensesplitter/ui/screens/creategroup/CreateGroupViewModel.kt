package com.nexxlabs.expensesplitter.ui.screens.creategroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.repository.GroupRepository
import com.nexxlabs.expensesplitter.ui.common.UiEvent
import com.nexxlabs.expensesplitter.ui.common.UiEventDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val uiEventDispatcher: UiEventDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState: StateFlow<CreateGroupUiState> = _uiState

    fun onGroupNameChange(value: String) {
        _uiState.update { it.copy(groupName = value) }
    }

    fun onMemberInputChange(value: String) {
        _uiState.update { it.copy(memberInput = value) }
    }

    fun addMember() {
        val name = _uiState.value.memberInput.trim()
        if (name.isEmpty()) return
        if (_uiState.value.members.any { it.equals(name, ignoreCase = true) }){
            viewModelScope.launch {
                uiEventDispatcher.emit(UiEvent.ShowToast("Member already added"))
            }
            return
        }

        _uiState.update {
            it.copy(
                members = it.members + name,
                memberInput = ""
            )
        }
    }

    fun removeMember(name: String) {
        _uiState.update {
            it.copy(members = it.members - name)
        }
    }

    fun createGroup(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.groupName.isBlank()) {
            _uiState.update { it.copy(error = "Group name is required") }
            return
        }

        if (state.members.size < 2) {
            _uiState.update { it.copy(error = "Add at least 2 members") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }

            groupRepository.createGroup(
                name = state.groupName.trim(),
                currency = state.currency,
                memberNames = state.members
            )

            onSuccess()
        }
    }
}

