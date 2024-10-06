package com.irinaabdriaeva.project.testappcommbank.account.data.model

data class TransactionGroup(
    val date: String,
    val relativeDate: String,
    val transactions: List<Transaction>
)