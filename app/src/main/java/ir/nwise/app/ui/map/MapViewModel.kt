package ir.nwise.app.ui.map

import androidx.lifecycle.viewModelScope
import ir.nwise.data.common.NoInternetConnectionException
import ir.nwise.domain.models.Car
import ir.nwise.domain.usecase.GetCarsUseCase
import ir.nwise.domain.usecase.base.UseCaseResult
import ir.nwise.app.ui.base.BaseViewModel


class MapViewModel(
    private val getCarsUseCase: GetCarsUseCase,
    private val networkManager: ir.nwise.data.common.utils.NetworkManager
) :
    BaseViewModel<MapViewState>() {

    fun getCars() {
        if (networkManager.hasNetwork()) {
            getCarsUseCase.execute(viewModelScope) {
                when (this) {
                    is UseCaseResult.Loading -> liveData.postValue(MapViewState.Loading)
                    is UseCaseResult.Success -> liveData.postValue(MapViewState.Loaded(this.data))
                    is UseCaseResult.Error -> liveData.postValue(MapViewState.Error(this.exception))
                }
            }
        } else
            liveData.postValue(MapViewState.Error(NoInternetConnectionException()))
    }
}

sealed class MapViewState {
    object Loading : MapViewState()
    data class Loaded(val cars: List<Car>) : MapViewState()
    data class Error(val throwable: Throwable) : MapViewState()
}