package com.irinaabdriaeva.project.testappcommbank.account.ui.screens

import android.R.style
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels.AccountViewModel
import com.irinaabdriaeva.project.testappcommbank.account.utils.getIconForCategory
import com.irinaabdriaeva.project.testappcommbank.ui.theme.CbaBlack
import com.irinaabdriaeva.project.testappcommbank.ui.theme.CbaGrey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    onTransactionClick: (Transaction) -> Unit
) {

    val account = viewModel.account.collectAsState().value
    val transactions = viewModel.transactions.collectAsState().value
    // Define the scroll behavior for the top bar
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    // Scaffold to manage the layout with a top bar and a content body
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(
                        text = account.accountName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
                // Non-scrollable content (Available, Balance, Pending)
                AccountBalanceSection(
                    available = account.available,
                    balance = account.balance,
                    pending = "200" //todo fix that to show the correct data
                )
                HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
                AccountDetailsSection(account.bsb, account.accountNumber)
                HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
                // Scrollable content (LazyColumn with onClick behavior)
                LazyColumn {
                    transactions.forEach { transactionGroup ->
                        // Use stickyHeader from Accompanist
                        item {
                            DateHeader(transactionGroup.date, transactionGroup.relativeDate)
                        }
//
//                        // Display individual transactions
//                        items(transactionGroup.transactions) { transaction ->
//                            TransactionItem(transaction = transaction)
//                        }
                    }
                }
//                LazyTransactionList(onTransactionClick = { transactionId ->
//                    // Handle transaction item click
//                    println("Clicked transaction ID: $transactionId")
//                })
            }
        }
    )
}

@Composable
fun AccountDetailsSection(bsb: String, accountNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "BSN ",
            style = MaterialTheme.typography.bodyLarge,
            color = CbaBlack,
            fontWeight = FontWeight.SemiBold
        )
        Text(bsb, style = MaterialTheme.typography.bodyLarge, color = CbaGrey)
        Text(
            text = " Account ",
            style = MaterialTheme.typography.bodyLarge,
            color = CbaBlack,
            fontWeight = FontWeight.SemiBold
        )
        Text(accountNumber, style = MaterialTheme.typography.bodyLarge, color = CbaGrey)
    }

}

@Composable
fun AccountBalanceSection(available: String, balance: String, pending: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                text = "Available",
                color = CbaGrey,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "$$available",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Text(
                text = "Balance",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "$$balance",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaBlack,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Text(
                text = "Pending",
                style = MaterialTheme.typography.bodyMedium,
                color = CbaGrey
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "$$pending",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TransactionItem(transactionId: String, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(transactionId) }
    ) {
        Text(
            text = transactionId,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DateHeader(date: String, relativeDate: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = CbaBlack
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = relativeDate,
            style = MaterialTheme.typography.bodyMedium,
            color = CbaGrey
        )
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Transaction icon based on category
        val iconRes = getIconForCategory(transaction.category)
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = transaction.category,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (transaction.isPending) {
                Text(
                    text = "PENDING",
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            Text(
                text = transaction.description,
                fontSize = 14.sp
            )
        }

        Text(
            text = "$${transaction.amount}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}