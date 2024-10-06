package com.irinaabdriaeva.project.testappcommbank.account.ui.usecases

import com.irinaabdriaeva.project.testappcommbank.account.data.model.Account
import com.irinaabdriaeva.project.testappcommbank.account.data.repository.AccountRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(): Account {
        return repository.getAccountData()
    }
}