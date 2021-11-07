package com.itunesapp.view.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.itunesapp.repository.model.Media
import com.itunesapp.repository.network.repository.Repository
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    private val repository: Repository,
    private val qTerm: String,
    private val qEntity: String?
    ): PagingSource<Int, Media>() {

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {

        return try {
            val nextPageNumber = params.key ?: STARTING_INDEX
            val response = repository.getMedias(qTerm, qEntity, (nextPageNumber * LIMIT), LIMIT)

            LoadResult.Page(
                data = response.results!!,
                prevKey = null,
                nextKey = if(response.results.isNullOrEmpty()) null else (nextPageNumber + 1)
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_INDEX    = 0
        const val LIMIT             = 20
    }
}