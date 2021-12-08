package com.dnc.androidgallery.core.network.error

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter

class ErrorCallAdapter(
    private val delegateAdapter: CallAdapter<Any, Call<*>>,
    private val gson: Gson
) : CallAdapter<Any, Call<*>> by delegateAdapter {
    override fun adapt(call: Call<Any>): Call<*> {
        return delegateAdapter.adapt(CallWithErrorHandling(call, gson))
    }
}
