package ir.nwise.app.di

import ir.nwise.app.ui.main.MainFragment
import org.koin.dsl.module

val uiModule = module {
    factory { MainFragment() }
}