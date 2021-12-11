package co.uk.healthera.healthera.domain.repository

import co.uk.healthera.healthera.rest.RestResponse
import co.uk.healthera.healthera.rest.fold
import co.uk.healthera.healthera.rest.model.RemediesResponse
import co.uk.healthera.healthera.rest.services.RestApiService
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
class RemedyRepositoryImpl @Inject constructor(private val _restApiService: RestApiService) :
    RemedyRepository {
    override suspend fun getRemedies(): RestResponse<RemediesResponse> {
        return _restApiService.getRemedies().fold()
    }
}