package ir.nwise.domain.usecase

import ir.nwise.domain.common.DispatcherProvider
import ir.nwise.domain.AppRepository
import ir.nwise.domain.models.Car
import ir.nwise.domain.usecase.base.UseCase

class GetCarsUseCase(
    private val appRepository: AppRepository, dispatchers: DispatcherProvider
) : UseCase<Any, List<Car>>(dispatchers) {
    override suspend fun executeOnBackground(param: Any?): List<Car> {
        return appRepository.getCars()
    }
}