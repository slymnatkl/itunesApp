package com.itunesapp.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itunesapp.core.R

fun ImageView.loadUrl(url: String?){

    val options = RequestOptions()
        .centerCrop()
        .placeholder(R.color.gray)
        .error(R.color.black)

    Glide.with(context)
        .load(url)
        .apply(options)
        .into(this)
}