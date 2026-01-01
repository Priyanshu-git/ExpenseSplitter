package com.nexxlabs.expensesplitter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexxlabs.expensesplitter.ui.about.AboutScreen
import com.nexxlabs.expensesplitter.ui.addexpense.AddExpenseScreen
import com.nexxlabs.expensesplitter.ui.creategroup.CreateGroupScreen
import com.nexxlabs.expensesplitter.ui.groupdetail.GroupDetailScreen
import com.nexxlabs.expensesplitter.ui.home.HomeScreen
import com.nexxlabs.expensesplitter.ui.privacy.PrivacyPolicyScreen
import com.nexxlabs.expensesplitter.ui.settings.SettingsScreen
import com.nexxlabs.expensesplitter.ui.summary.SummaryScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Home.route
    ) {

        composable(AppDestination.Home.route) {
            HomeScreen(
                onCreateGroup = {
                    navController.navigate(AppDestination.CreateGroup.route)
                },
                onOpenGroup = { groupId ->
                    navController.navigate(
                        AppDestination.GroupDetail.createRoute(groupId)
                    )
                },
                onOpenSettings = {
                    navController.navigate(AppDestination.Settings.route)
                }
            )
        }

        composable(AppDestination.CreateGroup.route) {
            CreateGroupScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestination.GroupDetail.route) {
            GroupDetailScreen(
                onAddExpense = { groupId ->
                    navController.navigate(
                        AppDestination.AddExpense.createRoute(groupId)
                    )
                },
                onViewSummary = { groupId ->
                    navController.navigate(
                        AppDestination.Summary.createRoute(groupId)
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestination.AddExpense.route) {
            AddExpenseScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestination.Summary.route) {
            SummaryScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestination.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onOpenAbout = {
                    navController.navigate(AppDestination.About.route)
                },
                onOpenPrivacy = {
                    navController.navigate(AppDestination.PrivacyPolicy.route)
                }
            )
        }

        composable(AppDestination.About.route) {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestination.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
