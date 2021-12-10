package com.dnc.androidgallery.core.network.error

import com.dnc.androidgallery.R
import com.dnc.androidgallery.core.providers.ResourceProvider
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class CallWithErrorHandling(
    private val delegate: Call<Any>,
    private val gson: Gson
) : Call<Any> by delegate, KoinComponent {

    private val resourceProvider: ResourceProvider by inject()

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(
                        call,
                        mapToAppException(HttpException(response))
                    )
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, mapToAppException(t))
            }
        })
    }

    override fun clone() = CallWithErrorHandling(delegate.clone(), gson)

    private fun mapToAppException(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                val responseBodyString = throwable.response()?.errorBody()?.string()
                when (throwable.code()) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        FromServerError(
                            responseBodyString ?: "",
                            throwable.code(),
                            throwable
                        )
                    }
                    HttpURLConnection.HTTP_NOT_FOUND -> {
                        val error: ErrorBodyResponse? = try {
                            gson.fromJson(
                                responseBodyString,
                                ErrorBodyResponse::class.java
                            )
                        } catch (e: Exception) {
                            null
                        }

                        FromServerError(
                            message = error?.message?.takeIf { it.isNotBlank() }
                                ?: responseBodyString?.takeIf { it.isNotBlank() }
                                ?: resourceProvider.getString(R.string.unexpected_server_error),
                            code = throwable.code(),
                            cause = throwable,
                            errorsMap = error?.errors
                        )
                    }
                    in 400..499 -> {
                        if (throwable.code() == 422 && responseBodyString != null) {
                            val errorsMap: Map<String, Any>? = try {
                                gson.fromJson(
                                    responseBodyString,
                                    ErrorBodyResponse::class.java
                                ).errors
                            } catch (e: Exception) {
                                null
                            }

                            FromServerError(
                                message = responseBodyString.takeIf { it.isNotBlank() }
                                    ?: resourceProvider.getString(R.string.unexpected_server_error),
                                code = throwable.code(),
                                cause = throwable,
                                errorsMap = errorsMap,
                                headers = throwable.response()?.headers()?.toMap()
                            )
                        } else {
                            FromServerError(
                                message = responseBodyString ?: "",
                                code = throwable.code(),
                                cause = throwable
                            )
                        }
                    }
                    else -> {
                        ServerError(throwable.code())
                    }
                }
            }
            else -> {
                throwable
            }
        }
    }
}
