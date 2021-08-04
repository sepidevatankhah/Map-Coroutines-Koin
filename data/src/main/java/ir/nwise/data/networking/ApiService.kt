package ir.nwise.data.networking


import ir.nwise.data.common.Config
import ir.nwise.domain.models.Car
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET(Config.API_VERSION)
    fun getCars(): Deferred<List<Car>>
}