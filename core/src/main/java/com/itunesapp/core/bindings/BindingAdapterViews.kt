package com.itunesapp.core.bindings

import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.itunesapp.core.components.EntityFilterPicker
import com.itunesapp.core.extensions.formatDate
import com.itunesapp.core.extensions.loadUrl
import java.text.NumberFormat
import java.util.*

@BindingAdapter("app:showFromUrl")
fun showFromUrl(view: ImageView, url: String?){
    view.loadUrl(url)
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

@BindingAdapter("app:setOnEntityFilterPickerListener")
fun setOnEntityFilterPickerListener(view: EntityFilterPicker, listener: EntityFilterPicker.EntityFilterPickerListener?){

    try {
        view.setOnEntityFilterPickerListener(listener)
    }
    catch (e: Exception){
        e.printStackTrace()
        view.setOnEntityFilterPickerListener(null)
    }
}

@BindingAdapter("app:setOnQueryTextListener")
fun setOnQueryTextListener(view: SearchView, listener: SearchView.OnQueryTextListener?){

    try {
        view.setOnQueryTextListener(listener)
    }
    catch (e: Exception){
        e.printStackTrace()
        view.setOnQueryTextListener(null)
    }
}