package ir.nwise.app.data.repository

import ir.nwise.app.domain.models.Car

interface AppRepository {
    suspend fun getCars(): List<Car>
}