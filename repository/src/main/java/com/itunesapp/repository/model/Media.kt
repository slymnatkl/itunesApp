package com.itunesapp.repository.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Media: Parcelable {

    @SerializedName("collectionId")
    val collectionId: Int? = null

    @SerializedName("trackId")
    val trackId: Int? = null

    @SerializedName("trackName")
    val trackName: String? = null

    @SerializedName("collectionName")
    val collectionName: String? = null

    @SerializedName("collectionPrice")
    val collectionPrice: String? = null

    @SerializedName("price")
    val price: String? = null

    @SerializedName("currency")
    val currency: String? = null

    @SerializedName("artworkUrl100")
    val artworkUrl100: String? = null

    @SerializedName("releaseDate")
    val releaseDate: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("shortDescription")
    val shortDescription: String? = null

    @SerializedName("longDescription")
    val longDescription: String? = null

    fun getShownName(): String?{

        if(!collectionName.isNullOrEmpty())
            return collectionName
        else if(!trackName.isNullOrEmpty())
            return trackName
        else
            return null
    }

    fun getShownPrice(): String?{

        if(!collectionPrice.isNullOrEmpty())
            return collectionPrice
        else if(!price.isNullOrEmpty())
            return price
        else
            return null
    }

    fun getShownDescription(): String?{

        if(!description.isNullOrEmpty())
            return description
        else if(!longDescription.isNullOrEmpty())
            return longDescription
        else if(!shortDescription.isNullOrEmpty())
            return shortDescription
        else
            return null
    }
}
