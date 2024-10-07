package com.irinaabdriaeva.project.testappcommbank.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.irinaabdriaeva.project.testappcommbank.account.ui.screens.AccountScreen
import com.irinaabdriaeva.project.testappcommbank.account.ui.screens.TransactionDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "account_screen"
    ) {
        // Navigation to AccountScreen
        composable("account_screen") {
            AccountScreen(onTransactionClick = { transaction ->
                navController.navigate("transaction_detail_screen/${transaction.id}")
            })
        }

        // Navigation to TransactionDetailScreen
        composable(
            route = "transaction_detail_screen/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            TransactionDetailScreen(transactionId = transactionId)
        }
    }
}