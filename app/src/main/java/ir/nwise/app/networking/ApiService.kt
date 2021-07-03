package ir.nwise.app.networking


import ir.nwise.app.common.Config
import ir.nwise.app.domain.models.Car
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET(Config.API_VERSION)
    fun getCars(): Deferred<List<Car>>
}