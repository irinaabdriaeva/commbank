package com.irinaabdriaeva.project.testappcommbank.account.ui.usecases

import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.data.repository.AccountRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(): List<Transaction> {
        return repository.getTransactions()
    }
}