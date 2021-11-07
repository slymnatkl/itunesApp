package com.itunesapp.core.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
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

@BindingAdapter("app:showReleaseDate")
fun showReleaseDate(view: TextView, text: String?){

    try {
        view.text = text?.formatDate("yyyy-MM-dd'T'HH:mm:ssXXX", "dd.MM.yyyy")
    }
    catch (e: Exception){
        e.printStackTrace()
        view.text = null
    }
}

@BindingAdapter("app:showPriceFormat")
fun showPriceFormat(view: TextView, text: String?){

    try {
        view.text = NumberFormat.getInstance(Locale.US).format(text?.toDouble())
    }
    catch (e: Exception){
        e.printStackTrace()
        view.text = null
    }
}