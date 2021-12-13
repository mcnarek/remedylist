package co.uk.healthera.healthera.di

import co.uk.healthera.healthera.domain.mappers.AdherenceMapper
import co.uk.healthera.healthera.domain.mappers.RemedyMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRemedyMapper(): RemedyMapper = RemedyMapper()

    @Provides
    @Singleton
    fun provideAdherenceMapper(): AdherenceMapper = AdherenceMapper()
}
