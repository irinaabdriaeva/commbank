package com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Account
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _account = MutableStateFlow(Account("", "", "", "", ""))
    val account: StateFlow<Account> = _account

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    init {
        viewModelScope.launch {
            _account.value = accountRepository.getAccountData()
            _transactions.value =
                accountRepository.getTransactions().sortedByDescending { it.effectiveDate }
        }
    }
}