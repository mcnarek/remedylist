package co.uk.healthera.healthera.domain.usecase

import co.uk.healthera.healthera.domain.model.AdherenceDataModel
import co.uk.healthera.healthera.domain.model.RemedyDataModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */

interface AdherenceUseCase {
    suspend fun getAdherenceList(): Flow<Map<String, List<AdherenceDataModel>>>

    suspend fun getRemedies(): List<RemedyDataModel>
}