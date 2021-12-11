package co.uk.healthera.healthera.domain.repository

import co.uk.healthera.healthera.rest.RestResponse
import co.uk.healthera.healthera.rest.model.AdherencesResponse
import co.uk.healthera.healthera.rest.model.RemediesResponse


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
interface AdherenceRepository {
    suspend fun getAdherence(): RestResponse<AdherencesResponse>
}

interface RemedyRepository {
    suspend fun getRemedies(): RestResponse<RemediesResponse>
}