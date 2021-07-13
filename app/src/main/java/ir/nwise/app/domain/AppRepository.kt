package ir.nwise.app.domain

import ir.nwise.app.domain.models.Car

interface AppRepository {
    suspend fun getCars(): List<Car>
}