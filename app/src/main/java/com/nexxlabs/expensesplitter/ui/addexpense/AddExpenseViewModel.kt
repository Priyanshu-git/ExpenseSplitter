package com.nexxlabs.expensesplitter.ui.addexpense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexxlabs.expensesplitter.data.local.dao.MemberDao
import com.nexxlabs.expensesplitter.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    memberDao: MemberDao,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val groupId: Long =
        savedStateHandle.get<String>("groupId")!!.toLong()

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState

    init {
        memberDao.observeMembers(groupId)
            .onEach { members ->
                _uiState.update {
                    it.copy(
                        members = members.map { m ->
                            MemberItem(
                                id = m.id,
                                name = m.name,
                                isSelected = true
                            )
                        },
                        paidByMemberId = members.firstOrNull()?.id
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun onAmountChange(value: String) {
        _uiState.update { it.copy(amount = value) }
    }

    fun onPaidByChange(memberId: Long) {
        _uiState.update { it.copy(paidByMemberId = memberId) }
    }

    fun toggleMember(memberId: Long) {
        _uiState.update {
            it.copy(
                members = it.members.map { m ->
                    if (m.id == memberId) {
                        m.copy(isSelected = !m.isSelected)
                    } else m
                }
            )
        }
    }

    fun saveExpense(onSuccess: () -> Unit) {
        val state = _uiState.value

        val amount = state.amount.toDoubleOrNull()
        val selectedMembers = state.members.filter { it.isSelected }

        if (state.title.isBlank()) {
            _uiState.update { it.copy(error = "Title is required") }
            return
        }

        if (amount == null || amount <= 0) {
            _uiState.update { it.copy(error = "Enter valid amount") }
            return
        }

        if (state.paidByMemberId == null) {
            _uiState.update { it.copy(error = "Select who paid") }
            return
        }

        if (selectedMembers.isEmpty()) {
            _uiState.update { it.copy(error = "Select at least one member") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }

            expenseRepository.addExpense(
                groupId = groupId,
                title = state.title.trim(),
                amount = amount,
                paidByMemberId = state.paidByMemberId,
                splitBetweenMemberIds = selectedMembers.map { it.id }
            )

            onSuccess()
        }
    }
}

