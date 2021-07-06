package ir.nwise.app.di

import ir.nwise.app.data.DefaultDispatcherProvider
import ir.nwise.app.data.repository.AppRepository
import ir.nwise.app.data.repository.AppRepositoryImp
import ir.nwise.app.domain.usecase.GetCarsUseCase
import ir.nwise.app.networking.ApiService
import kotlinx.coroutines.MainScope
import org.koin.dsl.module

val domainModule = module {

    fun provideRepository(apiService: ApiService): AppRepository {
        return AppRepositoryImp(apiService)
    }

    factory { provideRepository(get()) }
    factory {
        GetCarsUseCase(
            appRepository = get(),
            coroutineScope = MainScope(),
            dispatchers = DefaultDispatcherProvider()
        )
    }
}