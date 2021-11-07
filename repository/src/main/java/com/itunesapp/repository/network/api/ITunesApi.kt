package com.itunesapp.repository.network.api

import com.itunesapp.repository.model.Media
import com.itunesapp.repository.network.response.BaseListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {

    @GET("search")
    suspend fun getMedias(
        @Query("term") term: String,
        @Query("entity") entity: String?,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): BaseListResponse<Media>
}