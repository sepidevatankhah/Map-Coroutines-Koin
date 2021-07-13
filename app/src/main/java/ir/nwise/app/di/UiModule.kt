package ir.nwise.app.di

import ir.nwise.app.ui.detail.CarDetailFragment
import ir.nwise.app.ui.home.HomeFragment
import ir.nwise.app.ui.map.MapFragment
import ir.nwise.app.ui.map.MapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    factory { MapFragment() }
    factory { HomeFragment() }
    factory { CarDetailFragment() }

    viewModel { MapViewModel(get(),get()) }
}