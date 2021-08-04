package ir.nwise.domain.di

import ir.nwise.domain.common.DefaultDispatcherProvider
import ir.nwise.domain.usecase.GetCarsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetCarsUseCase(
            appRepository = get(),
            dispatchers = DefaultDispatcherProvider()
        )
    }
}