package com.dnc.androidgallery.di

import com.dnc.androidgallery.BuildConfig
import com.dnc.androidgallery.core.network.ApiService
import com.dnc.androidgallery.core.network.error.ErrorCallAdapterFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    factory { GsonConverterFactory.create(Gson()) }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(ErrorCallAdapterFactory(Gson()))
            .client(okHttpClient)
            .build()
    }

    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    factory { provideApiService(get()) }
}
