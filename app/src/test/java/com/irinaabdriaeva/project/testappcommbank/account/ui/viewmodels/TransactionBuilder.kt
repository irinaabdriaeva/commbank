package com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels

import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction

class TransactionBuilder {
    var id: String = "transaction-id"
    var amount: String = "100.0"
    var isPending: Boolean = false
    var description: String = "Sample Transaction"
    var category: String = "Shopping"
    var effectiveDate: String = "2022-01-01"

    fun build(): Transaction {
        return Transaction(id, amount, isPending, description, category, effectiveDate)
    }
}