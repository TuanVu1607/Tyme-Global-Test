package com.tymeglobal.test.currency_converter.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tymeglobal.test.currency_converter.BuildConfig
import com.tymeglobal.test.currency_converter.data.remote.interceptors.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val NETWORK_CALL_TIMEOUT = 60

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(logging)
        .connectTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().create()
                )
            )
            .client(client)
            .build()
    }
}