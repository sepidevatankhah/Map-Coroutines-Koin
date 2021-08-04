package ir.nwise.domain

import ir.nwise.domain.models.Car

interface AppRepository {
    suspend fun getCars(): List<Car>
}