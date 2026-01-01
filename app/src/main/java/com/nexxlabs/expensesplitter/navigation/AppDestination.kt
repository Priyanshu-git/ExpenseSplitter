package com.nexxlabs.expensesplitter.navigation

sealed class AppDestination(val route: String) {

    data object Home : AppDestination("home")

    data object CreateGroup : AppDestination("create_group")

    data object GroupDetail : AppDestination("group_detail/{groupId}") {
        fun createRoute(groupId: Long) = "group_detail/$groupId"
    }

    data object AddExpense : AppDestination("add_expense/{groupId}") {
        fun createRoute(groupId: Long) = "add_expense/$groupId"
    }

    data object Summary : AppDestination("summary/{groupId}") {
        fun createRoute(groupId: Long) = "summary/$groupId"
    }

    data object Settings : AppDestination("settings")

    data object About : AppDestination("about")

    data object PrivacyPolicy : AppDestination("privacy_policy")
}