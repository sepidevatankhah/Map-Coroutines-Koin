package ir.nwise.myapplication.di

import ir.nwise.myapplication.ui.main.MainFragment
import ir.nwise.myapplication.ui.main.MainViewModel
import org.koin.dsl.module

val uiModule = module {
    factory { MainFragment() }
}