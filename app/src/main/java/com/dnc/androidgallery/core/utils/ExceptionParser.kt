package com.dnc.androidgallery.core.utils

import com.dnc.androidgallery.R
import com.dnc.androidgallery.core.network.error.FromServerError
import com.dnc.androidgallery.core.network.error.ServerError
import com.dnc.androidgallery.core.providers.ResourceProvider
import org.json.JSONObject
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

class ExceptionParser(
    private val resourceProvider: ResourceProvider
) {
    fun parseError(throwable: Throwable): String {
        throwable.printStackTrace()
        return when (throwable) {
            is ServerError -> {
                return resourceProvider.getString(
                    R.string.unexpected_server_error,
                    throwable.code
                )
            }
            is FromServerError -> {
                return parseServerError(throwable)
            }
            is IOException -> {
                when (throwable) {
                    is ConnectException,
                    is SocketTimeoutException,
                    is SocketException -> {
                        return resourceProvider.getString(R.string.error_unable_to_communicate_to_server)
                    }
                    !is InterruptedIOException -> {
                        return resourceProvider.getString(R.string.error_no_internet_connection)
                    }
                    else -> throwable.localizedMessage
                }
            }
            else ->
                throwable.localizedMessage
                    ?: resourceProvider.getString(R.string.unexpected_server_error)
        }
    }

    private fun parseServerError(throwable: FromServerError): String {
        val errorMessage = try {
            val errorBodyResponse = JSONObject(throwable.message)
            errorBodyResponse.getString("message")
        } catch (e: Exception) {
            null
        }?.trim()
            ?: StringBuilder().apply {
                throwable.errorsMap?.values?.forEach {
                    val errorList = it as? List<*>
                    errorList?.forEach { error ->
                        append("$error\n")
                    } ?: it.toString()
                }
            }.toString().trim()
        return if (errorMessage.isNotBlank()) {
            errorMessage
        } else {
            throwable.message
        }
    }
}
