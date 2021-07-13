package ir.nwise.app.ui.map

import ir.nwise.app.common.NoInternetConnectionException
import ir.nwise.app.domain.NetworkManager
import ir.nwise.app.domain.models.Car
import ir.nwise.app.domain.usecase.GetCarsUseCase
import ir.nwise.app.domain.usecase.base.UseCaseResult
import ir.nwise.app.ui.base.BaseViewModel


class MapViewModel(
    private val getCarsUseCase: GetCarsUseCase,
    private val networkManager: NetworkManager
) :
    BaseViewModel<MapViewState>() {

    fun getCars() {
        if (networkManager.hasNetwork()) {
            getCarsUseCase.execute {
                when (this) {
                    is UseCaseResult.Loading -> liveData.postValue(MapViewState.Loading)
                    is UseCaseResult.Success -> liveData.postValue(MapViewState.Loaded(this.data))
                    is UseCaseResult.Error -> liveData.postValue(MapViewState.Error(this.exception))
                }
            }
        } else
            liveData.postValue(MapViewState.Error(NoInternetConnectionException()))
    }

    override fun onCleared() {
        super.onCleared()
        getCarsUseCase.unsubscribe()
    }
}

sealed class MapViewState {
    object Loading : MapViewState()
    data class Loaded(val cars: List<Car>) : MapViewState()
    data class Error(val throwable: Throwable) : MapViewState()
}