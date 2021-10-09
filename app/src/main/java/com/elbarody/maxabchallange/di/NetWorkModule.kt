package com.elbarody.maxabchallange.di

import com.elbarody.maxabchallange.BuildConfig
import com.elbarody.maxabchallange.app.App
import com.elbarody.maxabchallange.data.remote.CurrencyApi
import com.elbarody.maxabchallange.network_layer.NetWorkConstant.BASE_URL
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(120, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(120, TimeUnit.SECONDS)
        okHttpClient.readTimeout(120, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(120, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        okHttpClient.addInterceptor(ChuckInterceptor(App.instance.applicationContext))

        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun providesBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }
}