package com.irinaabdriaeva.project.testappcommbank.account.utils

import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.data.model.TransactionGroup
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

    fun groupTransactionsByDate(transactions: List<Transaction>): List<TransactionGroup> {
        val grouped = transactions.groupBy { it.effectiveDate }

        return grouped.map { (date, transactionsForDate) ->
            val relativeDate = getRelativeDate(date)
            TransactionGroup(
                date = formatDate(date),
                relativeDate = relativeDate,
                transactions = transactionsForDate
            )
        }.sortedByDescending { it.date }
    }

    private fun formatDate(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = sdf.parse(date)
        val dateFormat = SimpleDateFormat("EEE d MMM", Locale.getDefault())
        return dateFormat.format(parsedDate!!)
    }

    private fun getRelativeDate(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val transactionDate = sdf.parse(date) ?: return ""
        val today = Date()

        val diffInMillis = today.time - transactionDate.time
        val daysAgo = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)

        return when {
            daysAgo == 0L -> "Today"
            daysAgo == 1L -> "Yesterday"
            else -> "$daysAgo days ago"
        }
    }
}