package ir.maziz.batman

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import ir.maziz.batman.data.repo.BatmanMoviesRepository
import ir.maziz.batman.data.repo.BatmanMoviesRepositoryImpl
import ir.maziz.batman.data.repo.source.BatmanMoviesRemoteDataSource
import ir.maziz.batman.feature.main.MainViewModel
import ir.maziz.batman.services.http.createApiServiceInstance
import ir.maziz.batman.services.image.FrescoImageLoading
import ir.maziz.batman.services.image.ImageLoadingService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        val myModules = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoading() }
            single<BatmanMoviesRepository> {
                BatmanMoviesRepositoryImpl(
                    BatmanMoviesRemoteDataSource(
                        get()
                    )
                )
            }
            viewModel { MainViewModel(get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}