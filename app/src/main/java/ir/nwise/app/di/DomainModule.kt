package ir.nwise.app.di

import android.content.Context
import ir.nwise.app.common.NetworkManagerImp
import ir.nwise.app.data.DefaultDispatcherProvider
import ir.nwise.app.domain.AppRepository
import ir.nwise.app.data.repository.AppRepositoryImp
import ir.nwise.app.domain.NetworkManager
import ir.nwise.app.domain.usecase.GetCarsUseCase
import ir.nwise.app.networking.ApiService
import kotlinx.coroutines.MainScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val domainModule = module {

    fun provideRepository(apiService: ApiService): AppRepository {
        return AppRepositoryImp(apiService)
    }

    fun provideNetworkManger(context: Context): NetworkManager {
        return NetworkManagerImp(context)
    }

    single { provideNetworkManger(androidContext()) }

    factory { provideRepository(get()) }
    factory {
        GetCarsUseCase(
            appRepository = get(),
            coroutineScope = MainScope(),
            dispatchers = DefaultDispatcherProvider()
        )
    }
}