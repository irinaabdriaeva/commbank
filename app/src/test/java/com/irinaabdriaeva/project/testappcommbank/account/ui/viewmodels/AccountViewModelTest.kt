package com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels

import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetAccountDetailsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetTransactionsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels.testdata.AccountBuilder
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccountViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AccountViewModel
    private val getTransactionsUseCase: GetTransactionsUseCase = mockk()
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase = mockk()
    private val account = AccountBuilder().build()
    private val transaction = TransactionBuilder().build()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getAccountDetailsUseCase() } returns account
        coEvery { getTransactionsUseCase() } returns listOf(transaction)

        viewModel = AccountViewModel(getTransactionsUseCase, getAccountDetailsUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadData should load account details correctly`() = runTest {
        coEvery { getAccountDetailsUseCase() } returns account
        viewModel.loadData()

        val result = viewModel.account.first()
        assertEquals("123456", result.bsb)
        assertEquals("78901234", result.accountNumber)
        assertEquals("Complete Access", result.accountName)
        assertEquals("1000.00", result.balance)
        assertEquals("900.00", result.available)
    }

    @Test
    fun `loadData should load transactions grouped by date`() = runTest {
        val transaction1 = TransactionBuilder().apply {
            id = "abc"
            amount = "-10.0"
            isPending = true
            description = "Transaction 1"
            category = "Shopping"
            effectiveDate = "2022-01-10"
        }.build()

        val transaction2 = TransactionBuilder().apply {
            id = "dfe"
            amount = "-20.0"
            isPending = false
            description = "Transaction 2"
            category = "Groceries"
            effectiveDate = "2022-01-10"
        }.build()

        val transactions = listOf(transaction1, transaction2)

        coEvery { getTransactionsUseCase() } returns transactions

        viewModel.loadData()

        advanceUntilIdle()

        val result = viewModel.transactions.first()
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("Mon 10 Jan", result.first().date)
        assertEquals(2, result.first().transactions.size)
    }

    @Test
    fun `loadData should calculate the pending amount correctly`() = runTest {
        val transaction1 = TransactionBuilder().apply {
            id = "id1"
            amount = "-10.0"
            isPending = true
            description = "Transaction 1"
            category = "Shopping"
        }.build()

        val transaction2 = TransactionBuilder().apply {
            id = "id2"
            amount = "-20.0"
            isPending = false
            description = "Transaction 2"
            category = "Groceries"
        }.build()

        val transactions = listOf(transaction1, transaction2)

        coEvery { getTransactionsUseCase() } returns transactions

        viewModel.loadData()
        advanceUntilIdle()

        val pendingAmount = viewModel.pendingAmount.first()
        assertEquals("-10.0", pendingAmount)
    }

    @Test
    fun `getTransactionById should return correct transaction`() = runTest {
        val transaction1 = TransactionBuilder().apply {
            id = "id1"
            amount = "-10.0"
            isPending = true
            description = "Transaction 1"
            category = "Shopping"
        }.build()

        val transaction2 = TransactionBuilder().apply {
            id = "id2"
            amount = "-20.0"
            isPending = false
            description = "Transaction 2"
            category = "Groceries"
        }.build()

        val transactions = listOf(transaction1, transaction2)

        coEvery { getTransactionsUseCase() } returns transactions

        viewModel.loadData()
        advanceUntilIdle()

        val transaction = viewModel.getTransactionById("id1")
        assertNotNull(transaction)
        assertEquals("Transaction 1", transaction?.description)
    }
}