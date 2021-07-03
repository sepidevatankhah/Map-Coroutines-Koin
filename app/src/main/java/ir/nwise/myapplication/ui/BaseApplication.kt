package ir.nwise.myapplication.ui

import android.app.Application
import ir.nwise.myapplication.di.domainModule
import ir.nwise.myapplication.di.networkModule
import ir.nwise.myapplication.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    networkModule,
                    domainModule,
                    uiModule
                )
            )
        }
    }
}