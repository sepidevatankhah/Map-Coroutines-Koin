package ir.nwise.app.data.repository

import ir.nwise.app.networking.ApiService
import ir.nwise.app.domain.models.Car


class AppRepositoryImp(private val apiService: ApiService) : AppRepository {

    override suspend fun getCars(): List<Car> {
        return apiService.getCars().await()
    }
}