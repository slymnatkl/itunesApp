package com.itunesapp.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String): Date? {

    return SimpleDateFormat(format, Locale.getDefault()).parse(this)
}

fun String.formatDate(formatOld: String, formatNew: String): String? {

    val dateFormatter = SimpleDateFormat(formatNew, Locale.getDefault())

    val date = toDate(formatOld)
    if(date != null)
        return dateFormatter.format(date)
    else
        return null
}