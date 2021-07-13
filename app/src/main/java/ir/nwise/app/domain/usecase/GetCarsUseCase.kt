package ir.nwise.app.domain.usecase

import ir.nwise.app.data.DispatcherProvider
import ir.nwise.app.domain.AppRepository
import ir.nwise.app.domain.models.Car
import ir.nwise.app.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineScope

class GetCarsUseCase(
    private val appRepository: AppRepository,
    coroutineScope: CoroutineScope,
    dispatchers: DispatcherProvider
) : UseCase<Any, List<Car>>(coroutineScope, dispatchers) {
    override suspend fun executeOnBackground(param: Any?): List<Car> {
        return appRepository.getCars()
    }
}