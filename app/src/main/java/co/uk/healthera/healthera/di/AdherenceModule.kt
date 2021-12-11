package co.uk.healthera.healthera.di

import co.uk.healthera.healthera.domain.repository.AdherenceRepository
import co.uk.healthera.healthera.domain.repository.AdherenceRepositoryImpl
import co.uk.healthera.healthera.domain.repository.RemedyRepository
import co.uk.healthera.healthera.domain.repository.RemedyRepositoryImpl
import co.uk.healthera.healthera.domain.usecase.AdherenceUseCase
import co.uk.healthera.healthera.domain.usecase.AdherenceUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
@Module
@InstallIn(ViewModelComponent::class)
interface AdherenceModule {
    @Binds
    @ViewModelScoped
    fun provideAdherenceRepository(impl: AdherenceRepositoryImpl): AdherenceRepository

    @Binds
    @ViewModelScoped
    fun provideRemedyRepository(impl: RemedyRepositoryImpl): RemedyRepository

    @Binds
    @ViewModelScoped
    fun provideAdherenceUseCase(impl: AdherenceUseCaseImpl): AdherenceUseCase
}