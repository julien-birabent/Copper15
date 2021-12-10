package com.example.copper15.di.component

import android.app.Application
import com.example.copper15.app.ThisApplication
import com.example.copper15.di.module.*
import com.example.copper15.domain.UseCase
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivityBuilderModule::class,
        DataSourceModule::class,
        RepositoryModule::class]
)
interface ApplicationComponent {

    fun inject(application: ThisApplication)

    fun inject(useCase: UseCase.Injector)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}