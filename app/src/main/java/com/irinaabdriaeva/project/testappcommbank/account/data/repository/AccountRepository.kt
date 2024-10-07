package com.irinaabdriaeva.project.testappcommbank.account.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Account
import com.irinaabdriaeva.project.testappcommbank.account.data.model.Transaction
import com.irinaabdriaeva.project.testappcommbank.account.utils.JsonUtils
import org.json.JSONObject
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    fun getAccountData(): Account {
        val jsonString = JsonUtils.loadJSONFromAsset(context, "exercise.json")
        val jsonObject = JSONObject(jsonString)
        return gson.fromJson(jsonObject.getJSONObject("account").toString(), Account::class.java)
    }

    fun getTransactions(): List<Transaction> {
        val jsonString = JsonUtils.loadJSONFromAsset(context, "exercise.json")
        val jsonObject = JSONObject(jsonString)
        val transactions = jsonObject.getJSONArray("transactions")
        val type = object : TypeToken<List<Transaction>>() {}.type
        return gson.fromJson(transactions.toString(), type)
    }
}