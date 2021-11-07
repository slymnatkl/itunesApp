package com.itunesapp.repository.network.repository

import com.itunesapp.repository.network.api.ITunesApi

class Repository(private val apiService: ITunesApi){

    suspend fun getMedias(term: String, offset: Int, limit: Int) = apiService.getMedias(term, offset, limit)
}