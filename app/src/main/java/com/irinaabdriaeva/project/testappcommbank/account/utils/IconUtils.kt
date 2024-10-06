package com.irinaabdriaeva.project.testappcommbank.account.utils

import com.irinaabdriaeva.project.testappcommbank.R

fun getIconForCategory(category: String): Int {

    return when (category) {
        "shopping" -> R.drawable.icon_category_shopping
        "business" -> R.drawable.icon_category_business
        "entertainment" -> R.drawable.icon_category_entertainment
        "groceries" -> R.drawable.icon_category_groceries
        "transport" -> R.drawable.icon_category_transportation
        "education" -> R.drawable.icon_category_education
        "cards" -> R.drawable.icon_category_cards
        "cash" -> R.drawable.icon_category_cash
        "travel" -> R.drawable.icon_category_travel
        "eating_out" -> R.drawable.icon_category_eating_out

        else -> R.drawable.icon_category_uncategorised
    }
}