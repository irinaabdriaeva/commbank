package com.irinaabdriaeva.project.testappcommbank.account.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

object JsonUtils {

    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}