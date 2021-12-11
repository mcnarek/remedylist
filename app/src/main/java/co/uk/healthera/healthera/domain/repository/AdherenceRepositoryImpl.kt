package co.uk.healthera.healthera.domain.repository

import co.uk.healthera.healthera.rest.RestResponse
import co.uk.healthera.healthera.rest.fold
import co.uk.healthera.healthera.rest.model.AdherencesResponse
import co.uk.healthera.healthera.rest.services.RestApiService
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
class AdherenceRepositoryImpl @Inject constructor(private val _restApiService: RestApiService) :
    AdherenceRepository {
    override suspend fun getAdherence(): RestResponse<AdherencesResponse> {
        return _restApiService.getAdherences().fold()
    }
}