package ir.nwise.app.domain.usecase

import ir.nwise.app.data.DispatcherProvider
import ir.nwise.app.domain.AppRepository
import ir.nwise.app.domain.models.Car
import ir.nwise.app.domain.usecase.base.UseCase

class GetCarsUseCase(
    private val appRepository: AppRepository, dispatchers: DispatcherProvider
) : UseCase<Any, List<Car>>(dispatchers) {
    override suspend fun executeOnBackground(param: Any?): List<Car> {
        return appRepository.getCars()
    }
}