package com.itunesapp.repository.network.repository

import com.itunesapp.repository.network.api.ITunesApi

class Repository(private val apiService: ITunesApi){

    suspend fun getMedias(term: String, limit: Int, offset: Int) = apiService.getMedias(term, limit, offset)
}