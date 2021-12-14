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

private const val format = "json"
private const val callbackStatus = "1"
private const val perPage = "9"

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
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.FLICKR_API_TOKEN)
                    .addQueryParameter("format", format)
                    .addQueryParameter("per_page", perPage)
                    .addQueryParameter("nojsoncallback", callbackStatus)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
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
