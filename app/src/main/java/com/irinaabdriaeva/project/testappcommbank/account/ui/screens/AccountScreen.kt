package com.irinaabdriaeva.project.testappcommbank.account.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onTransactionClick: (Transaction) -> Unit
) {

    val account = viewModel.account.collectAsState().value
    val transactions = viewModel.transactions.collectAsState().value

    Column {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = account.accountName, style = MaterialTheme.typography.headlineMedium)
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