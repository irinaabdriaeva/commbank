package com.irinaabdriaeva.project.testappcommbank.account.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onTransactionClick: (Transaction) -> Unit
) {

    val account = viewModel.account.collectAsState().value
    val transactions = viewModel.transactions.collectAsState().value

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = account.accountName)
                },
                scrollBehavior = scrollBehavior,
            )
        }
    )

    { innerPadding ->
        Text(
            "Click the back button to pop from the back stack.",
            modifier = Modifier.padding(innerPadding),
        )
    }

    Column {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Available: $${account.available}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Balance: $${account.balance}")
            Text(text = "BSB ${account.bsb} Account ${account.accountNumber}")
        }

//        items(transactions.groupBy { it.effectiveDate }.toList()) { (date, groupedTransactions) ->
//            Text(text = date, style = MaterialTheme.typography.subtitle1)
//            groupedTransactions.forEach { transaction ->
//                TransactionItem(transaction, onTransactionClick)
//            }
//        }
    }
}