package ir.nwise.app.ui

import android.app.Application
import ir.nwise.app.di.uiModule
import ir.nwise.data.di.networkModule
import ir.nwise.domain.di.domainModule
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

