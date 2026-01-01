package com.nexxlabs.expensesplitter.data.repository

import com.nexxlabs.expensesplitter.data.local.dao.ExpenseDao
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseSplitDao
import com.nexxlabs.expensesplitter.data.local.dao.MemberDao
import javax.inject.Inject

data class MemberBalance(
    val memberId: Long,
    val name: String,
    val balance: Double
)

data class Settlement(
    val from: String,
    val to: String,
    val amount: Double
)

class SettlementRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val splitDao: ExpenseSplitDao,
    private val memberDao: MemberDao
) {

    suspend fun calculateBalances(groupId: Long): List<MemberBalance> {
        val members = memberDao.observeMembers(groupId)
        // We'll collect this properly in ViewModel (Flow → State)
        throw NotImplementedError("Used via ViewModel")
    }

    /**
     * Simple greedy settlement algorithm:
     * debtors pay creditors until balances reach zero
     */
    fun calculateSettlements(
        balances: List<MemberBalance>
    ): List<Settlement> {

        val debtors = balances.filter { it.balance < 0 }.toMutableList()
        val creditors = balances.filter { it.balance > 0 }.toMutableList()

        val settlements = mutableListOf<Settlement>()

        var i = 0
        var j = 0

        while (i < debtors.size && j < creditors.size) {
            val debtor = debtors[i]
            val creditor = creditors[j]

            val amount = minOf(-debtor.balance, creditor.balance)

            settlements.add(
                Settlement(
                    from = debtor.name,
                    to = creditor.name,
                    amount = amount
                )
            )

            debtors[i] = debtor.copy(balance = debtor.balance + amount)
            creditors[j] = creditor.copy(balance = creditor.balance - amount)

            if (debtors[i].balance == 0.0) i++
            if (creditors[j].balance == 0.0) j++
        }

        return settlements
    }
}
