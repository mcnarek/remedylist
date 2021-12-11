package co.uk.healthera.healthera.domain.model


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
data class RemedyDataModel(
    val id: String,

    val dateCreated: String = "",

    val isOnGoing: Boolean = false,

    val name: String = "",

    val dateStart: String = "",

    val dateEnd: String = "",

    val desc: String? = null
)
