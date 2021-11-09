package com.itunesapp.view.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.itunesapp.repository.model.Media
import com.itunesapp.repository.network.repository.Repository
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    private val repository: Repository,
    private val qTerm: String,
    private val qMedia: String?
    ): PagingSource<Int, Media>() {

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {

        return try {
            val pageNumber = params.key ?: STARTING_INDEX
            val response = repository.getMedias(qTerm, qMedia, (pageNumber * LIMIT), LIMIT)

            val nextPageNumber = response.results?.let { pageNumber + 1 } ?: run { null }

            LoadResult.Page(
                data = response.results ?: run { emptyList() },
                prevKey = null,
                nextKey = nextPageNumber
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