package ir.nwise.app.ui.home

import ir.nwise.app.domain.models.Car
import ir.nwise.app.domain.usecase.GetCarsUseCase
import ir.nwise.app.domain.usecase.base.UseCaseResult
import ir.nwise.app.ui.base.BaseViewModel


class HomeViewModel(private val getCarsUseCase: GetCarsUseCase) :
    BaseViewModel<HomeViewState>() {
    fun getCars() {
        getCarsUseCase.execute {
            when (this) {
                is UseCaseResult.Loading -> liveData.postValue(HomeViewState.Loading)
                is UseCaseResult.Success -> liveData.postValue(HomeViewState.Loaded(this.data))
                is UseCaseResult.Error -> liveData.postValue(HomeViewState.Error(this.exception))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCarsUseCase.unsubscribe()
    }
}

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Loaded(val cars: List<Car>) : HomeViewState()
    data class Error(val throwable: Throwable) : HomeViewState()
}