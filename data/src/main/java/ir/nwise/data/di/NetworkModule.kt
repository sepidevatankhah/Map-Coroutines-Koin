package ir.nwise.data.di

import android.content.Context
import ir.nwise.data.common.utils.NetworkManager
import ir.nwise.data.common.utils.NetworkManagerImp
import ir.nwise.data.networking.ApiService
import ir.nwise.data.networking.provideApiService
import ir.nwise.data.networking.provideLoggingInterceptor
import ir.nwise.data.networking.provideOkHttpClient
import ir.nwise.data.networking.provideRetrofit
import ir.nwise.data.networking.providesGsonConverterFactory
import ir.nwise.data.repository.AppRepositoryImp
import ir.nwise.domain.AppRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    factory { provideOkHttpClient(get()) }
    factory { provideApiService(get()) }
    factory { provideLoggingInterceptor() }
    single { providesGsonConverterFactory() }
    single { provideRetrofit(get()) }

    fun provideRepository(apiService: ApiService): AppRepository {
        return AppRepositoryImp(apiService)
    }

    fun provideNetworkManger(context: Context): NetworkManager {
        return NetworkManagerImp(context)
    }

    single { provideNetworkManger(androidContext()) }

    factory { provideRepository(get()) }

}