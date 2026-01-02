package com.nexxlabs.expensesplitter.data.mock

import com.nexxlabs.expensesplitter.ui.screens.home.GroupItem

object HomeScreenMockData {
    val groupItem = GroupItem(
        id = 1L,
        name = "3 BHK",
        memberCount = 2,
        expenseCount = 10,
        totalExpense = "15000"
    )

    val groupItemList = listOf(groupItem, groupItem, groupItem)

}