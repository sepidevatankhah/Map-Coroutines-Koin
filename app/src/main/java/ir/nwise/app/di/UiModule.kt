package ir.nwise.app.di

import ir.nwise.app.ui.main.MainFragment
import ir.nwise.app.ui.main.MapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    factory { MainFragment() }

    viewModel { MapViewModel(get()) }
}