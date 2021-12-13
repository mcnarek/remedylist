package co.uk.healthera.healthera.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
fun Long?.parseAsDate(): String {
    if (this == null || this == 0L) return "-"

    return try {
        SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(Date(this * 1000))
    } catch (e: Exception) {
        e.printStackTrace()
        "-"
    }
}

fun Long?.parseTime(): String {
    if (this == null || this == 0L) return "-"

    return try {
        SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(this * 1000))
    } catch (e: Exception) {
        e.printStackTrace()
        "-"
    }
}