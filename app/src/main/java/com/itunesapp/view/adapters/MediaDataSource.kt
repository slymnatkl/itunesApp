package com.itunesapp.view.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.itunesapp.repository.model.Media
import com.itunesapp.repository.network.repository.Repository
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    private val repository: Repository,
    private val query: String
    ): PagingSource<Int, Media>() {

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {

        return try {
            val nextPageNumber = params.key ?: STARTING_INDEX
            val response = repository.getMedias(query, (nextPageNumber * LIMIT), LIMIT)

            LoadResult.Page(
                data = response.results!!,
                prevKey = null,
                nextKey = nextPageNumber + 1
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