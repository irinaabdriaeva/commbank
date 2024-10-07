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
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccountViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AccountViewModel
    private val getTransactionsUseCase: GetTransactionsUseCase = mockk()
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase = mockk()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AccountViewModel(getTransactionsUseCase, getAccountDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadData should load account details correctly`() = runTest {
        val account = AccountBuilder()
            .apply {
                bsb = "123456"
                accountNumber = "78901234"
                accountName = "Test Account"
                balance = "1000.0"
                available = "900.0"
            }.build()

        coEvery { getAccountDetailsUseCase() } returns account
        viewModel.loadData()

        val result = viewModel.account.first()
        assertEquals("123456", result.bsb)
        assertEquals("78901234", result.accountNumber)
        assertEquals("Test Account", result.accountName)
        assertEquals("1000.0", result.balance)
        assertEquals("900.0", result.available)
    }

    @Test
    fun `loadData should load transactions grouped by date`() = runTest {
        val transaction1 = TransactionBuilder().apply {
            id = "id1"
            amount = "-10.0"
            isPending = true
            description = "Transaction 1"
            category = "Shopping"
            effectiveDate = "2022-10-01"
        }.build()

        val transaction2 = TransactionBuilder().apply {
            id = "id2"
            amount = "-20.0"
            isPending = false
            description = "Transaction 2"
            category = "Groceries"
            effectiveDate = "2022-10-01"
        }.build()

        val transactions = listOf(transaction1, transaction2)

        coEvery { getTransactionsUseCase() } returns transactions

        viewModel.loadData()

        val result = viewModel.transactions.first()
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("2022-10-01", result.first().date)
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

        val transaction = viewModel.getTransactionById("id1")
        assertNotNull(transaction)
        assertEquals("Transaction 1", transaction?.description)
    }
}