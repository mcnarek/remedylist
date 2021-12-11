package co.uk.healthera.healthera.domain.usecase

import co.uk.healthera.healthera.domain.mappers.AdherenceMapper
import co.uk.healthera.healthera.domain.mappers.RemedyMapper
import co.uk.healthera.healthera.domain.model.AdherenceDataModel
import co.uk.healthera.healthera.domain.model.RemedyDataModel
import co.uk.healthera.healthera.domain.repository.AdherenceRepository
import co.uk.healthera.healthera.domain.repository.RemedyRepository
import co.uk.healthera.healthera.rest.RestResponse
import co.uk.healthera.healthera.rest.model.AdherencesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
class AdherenceUseCaseImpl @Inject constructor(
    private val _adherenceRepository: AdherenceRepository,
    private val _remedyRepository: RemedyRepository,
    private val _remedyMapper: RemedyMapper,
    private val _adherenceMapper: AdherenceMapper,
) : AdherenceUseCase {
    override suspend fun getAdherenceList(): Flow<Map<String, List<AdherenceDataModel>>> {
        return when (val adherence: RestResponse<AdherencesResponse> =
            _adherenceRepository.getAdherence()) {
            is RestResponse.Error -> flow {
                throw adherence.throwable ?: Throwable(adherence.error)
            }
            is RestResponse.Success -> {
                flow<Map<String, List<AdherenceDataModel>>> {
                    val data: List<AdherenceDataModel> = adherence.data.data?.map {
                        val adherenceDataModel: AdherenceDataModel = _adherenceMapper.map(it,
                            getRemedies().find { remedy -> remedy.id == it.remedyId })

                        adherenceDataModel
                    } ?: emptyList()

                    val mappedList: Map<String, List<AdherenceDataModel>> =
                        data.groupBy { it.alarmTime }
                    emit(mappedList)
                }
            }
        }
    }

    override suspend fun getRemedies(): List<RemedyDataModel> {
        return when (val remedies = _remedyRepository.getRemedies()) {
            is RestResponse.Error -> emptyList()
            is RestResponse.Success -> remedies.data.data?.map { _remedyMapper.map(it) }
                ?: emptyList()
        }
    }
}