package co.uk.healthera.healthera.rest.services

import co.uk.healthera.healthera.rest.model.AdherencesResponse
import co.uk.healthera.healthera.rest.model.RemediesResponse
import retrofit2.Response
import retrofit2.http.GET


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
interface RestApiService {
    @GET("adherences")
    suspend fun getAdherences(): Response<AdherencesResponse>

    @GET("remedies")
    suspend fun getRemedies(): Response<RemediesResponse>
}