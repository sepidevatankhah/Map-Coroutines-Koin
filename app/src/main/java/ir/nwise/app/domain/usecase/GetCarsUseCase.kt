package ir.nwise.app.domain.usecase

import ir.nwise.app.data.DefaultDispatcherProvider
import ir.nwise.app.data.DispatcherProvider
import ir.nwise.app.data.repository.AppRepository
import ir.nwise.app.domain.models.Car
import ir.nwise.app.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class GetCarsUseCase(
    private val appRepository: AppRepository,
    coroutineScope: CoroutineScope = MainScope(),
    dispatchers: DispatcherProvider = DefaultDispatcherProvider()
) : UseCase<Any, List<Car>>(coroutineScope, dispatchers) {
    override suspend fun executeOnBackground(param: Any?): List<Car> {
        return appRepository.getCars()
    }
}