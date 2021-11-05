package com.itunesapp.repository.network.response

import com.google.gson.annotations.SerializedName

open class BaseListResponse <T> {

    @SerializedName("resultCount")
    val resultCount: Int? = null

    @SerializedName("results")
    val results: List<T>? = null
}
