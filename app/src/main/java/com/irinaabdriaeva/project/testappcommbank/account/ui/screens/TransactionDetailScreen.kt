package com.irinaabdriaeva.project.testappcommbank.account.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels.AccountViewModel
import com.irinaabdriaeva.project.testappcommbank.ui.theme.CbaGrey

@Composable
fun TransactionDetailScreen(
    transactionId: String?,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val transaction = viewModel.getTransactionById(transactionId)

    transaction?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Transaction ID: ${transaction.id}",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
            Text(
                text = "Description: ${transaction.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
            Text(
                text = "Amount: $${transaction.amount}",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
            Text(
                text = "Category: ${transaction.category}",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
            Text(
                text = "Date: ${transaction.effectiveDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
        }
    }
}