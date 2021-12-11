package co.uk.healthera.healthera.domain.mappers

import co.uk.healthera.healthera.domain.model.RemedyDataModel
import co.uk.healthera.healthera.rest.model.RemedyData
import co.uk.healthera.healthera.utils.parseAsDate
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
class RemedyMapper @Inject constructor() {
    fun map(from: RemedyData): RemedyDataModel {
        return from.let {
            RemedyDataModel(
                id = it.remedyId ?: "",
                dateCreated = it.dateCreated.parseAsDate(),
                dateEnd = it.endDate.parseAsDate(),
                dateStart = it.startDate.parseAsDate(),
                name = it.medicineName ?: "",
                isOnGoing = it.isOngoing,
                desc = it.instruction
            )
        }
    }
}