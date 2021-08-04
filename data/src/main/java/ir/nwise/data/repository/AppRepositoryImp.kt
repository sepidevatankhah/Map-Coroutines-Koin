package ir.nwise.data.repository

import ir.nwise.data.networking.ApiService
import ir.nwise.domain.AppRepository
import ir.nwise.domain.models.Car

class AppRepositoryImp(private val apiService: ApiService) : AppRepository {

    override suspend fun getCars(): List<Car> {
        return apiService.getCars().await()
    }
}