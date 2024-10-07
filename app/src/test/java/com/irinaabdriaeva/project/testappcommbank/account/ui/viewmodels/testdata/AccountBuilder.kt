package com.irinaabdriaeva.project.testappcommbank.account.ui.viewmodels.testdata

import com.irinaabdriaeva.project.testappcommbank.account.data.model.Account

class AccountBuilder {
    var bsb: String = "123456"
    var accountNumber: String = "78901234"
    var balance: String = "1000.00"
    var available: String = "900.00"
    var accountName: String = "Complete Access"

    fun build(): Account {
        return Account(bsb, accountNumber, balance, available, accountName)
    }
}