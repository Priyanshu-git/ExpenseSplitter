package com.nexxlabs.expensesplitter.ui.creategroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState: StateFlow<CreateGroupUiState> = _uiState

    fun onGroupNameChange(name: String) {
        _uiState.value = _uiState.value.copy(groupName = name)
    }

    fun onMemberNameChange(index: Int, name: String) {
        val updated = _uiState.value.members.toMutableList()
        updated[index] = name
        _uiState.value = _uiState.value.copy(members = updated)
    }

    fun addMember() {
        _uiState.value =
            _uiState.value.copy(members = _uiState.value.members + "")
    }

    fun removeMember(index: Int) {
        val updated = _uiState.value.members.toMutableList()
        updated.removeAt(index)
        _uiState.value = _uiState.value.copy(members = updated)
    }

    fun saveGroup(onSuccess: () -> Unit) {
        val state = _uiState.value
        val validMembers = state.members.map { it.trim() }.filter { it.isNotEmpty() }

        if (state.groupName.isBlank()) {
            _uiState.value = state.copy(error = "Group name cannot be empty")
            return
        }

        if (validMembers.size < 2) {
            _uiState.value = state.copy(error = "Add at least two members")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isSaving = true, error = null)

            groupRepository.createGroup(
                name = state.groupName.trim(),
                currency = "INR",
                memberNames = validMembers
            )

            onSuccess()
        }
    }
}
