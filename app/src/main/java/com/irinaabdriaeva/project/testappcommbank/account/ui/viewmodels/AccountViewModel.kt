package com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Account
import com.irinaabdriaeva.project.testappcommbank.account.data.model.TransactionGroup
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetAccountDetailsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetTransactionsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase
) : ViewModel() {

    private val _account = MutableStateFlow<Account>(Account("", "", "", "", ""))
    val account: StateFlow<Account> = _account

    private val _transactions = MutableStateFlow<List<TransactionGroup>>(emptyList())
    val transactions: StateFlow<List<TransactionGroup>> = _transactions

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Load account details
            _account.value = getAccountDetailsUseCase()

            // Load and group transactions by date
            val transactions = getTransactionsUseCase()
            _transactions.value = DateUtils.groupTransactionsByDate(transactions)
        }
    }
}