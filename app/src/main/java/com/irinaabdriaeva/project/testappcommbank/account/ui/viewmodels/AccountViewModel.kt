package com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels

import androidx.annotation.OpenForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Account
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.data.model.TransactionGroup
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetAccountDetailsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetTransactionsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase
) : ViewModel() {

    private val _account = MutableStateFlow(Account("", "", "", "", ""))
    val account: StateFlow<Account> = _account

    private val _transactions = MutableStateFlow<List<TransactionGroup>>(emptyList())
    val transactions: StateFlow<List<TransactionGroup>> = _transactions

    private val _pendingAmount = MutableStateFlow(0.0)
    val pendingAmount: StateFlow<Double> = _pendingAmount

    init {
        loadData()
    }

    fun getTransactionById(transactionId: String?): Transaction? {
        return transactions.value
            .flatMap { it.transactions }
            .find { it.id == transactionId }
    }

    private fun getPendingAmount(transactions: List<Transaction>): Double {
        val amount = transactions
            .filter { it.isPending }
            .sumOf { it.amount.toDouble() }
        return amount
    }

    fun loadData() {
        viewModelScope.launch {
            // Load account details
            _account.value = getAccountDetailsUseCase()

            // Load and group transactions by date
            val transactions = getTransactionsUseCase()
            _transactions.value = DateUtils.groupTransactionsByDate(transactions)

            _pendingAmount.value = getPendingAmount(transactions)
        }
    }
}