package co.uk.healthera.healthera.domain.mappers

import co.uk.healthera.healthera.domain.model.AdherenceDataModel
import co.uk.healthera.healthera.domain.model.RemedyDataModel
import co.uk.healthera.healthera.rest.model.AdherenceData
import co.uk.healthera.healthera.utils.parseAsDate
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
class AdherenceMapper @Inject constructor() {
    fun map(from: AdherenceData, remedyDataModel: RemedyDataModel?): AdherenceDataModel {
        return from.let {
            AdherenceDataModel(
                id = it.adherenceId ?: "",
                remedyId = it.remedyId ?: "",
                doseQuantity = it.doseQuantity,
                actionTime = it.actionTime.parseAsDate(),
                action = it.action,
                alarmTime = it.alarmTime.parseAsDate(),
                remedy = remedyDataModel
            )
        }
    }
}