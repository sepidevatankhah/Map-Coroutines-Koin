package ir.nwise.app.networking

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import ir.nwise.app.common.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

    return OkHttpClient()
        .newBuilder()
        .addInterceptor { chain ->
            // Get the request from the chain.
            var request = chain.request()
            val url = request.url.newBuilder()
                .build()

            request = request.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)