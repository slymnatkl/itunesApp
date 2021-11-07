package com.itunesapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.itunesapp.R
import com.itunesapp.repository.model.Media
import com.itunesapp.repository.network.repository.Repository
import com.itunesapp.repository.network.response.ErrorResponse
import com.itunesapp.view.adapters.MediaAdapter
import com.itunesapp.view.adapters.MediaDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): BaseViewModel(){


    //<editor-fold desc="Variables">

    private var context : Context? = null

    private lateinit var medias: Flow<PagingData<Media>>
    private var query: String? = null
    val adapterMedia = MediaAdapter()

    //</editor-fold>

    //<editor-fold desc="Init Views">

    fun setContext(context: Context){

        this@HomeViewModel.context = context
    }

    init {
        launch {

            adapterMedia.loadStateFlow.collectLatest { loadStates ->

                loading.value = (loadStates.refresh is LoadState.Loading)

                if(loadStates.refresh is LoadState.Error)
                    error.value = ErrorResponse((loadStates.refresh as LoadState.Error).error.localizedMessage)
                else
                    error.value = null
            }
        }

        fetchMedias()
    }

    //</editor-fold>

    //<editor-fold desc="Search Query">

    private var searchJob: Job? = null

    fun onQueryTextChange(query: String?): Boolean {

        this@HomeViewModel.query = query

        searchJob?.cancel()

        if (!query.isNullOrEmpty() && query.length >= 2){

            searchJob = launch {

                delay(700)
                refreshPage()
            }
        }

        return true
    }

    fun onQueryTextSubmit(query: String?): Boolean {

        this@HomeViewModel.query = query

        searchJob?.cancel()
        refreshPage()

        return true
    }

    fun refreshPage(){

        if (!query.isNullOrEmpty()){

            fetchMedias()
        }
        else{
            loading.value = false
            context?.let {
                error.value = ErrorResponse(it.getString(R.string.search_hint))
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="Fetch Data">

    private fun fetchMedias(){

        query?.let {

            medias = Pager(PagingConfig(pageSize = MediaDataSource.LIMIT)){
                MediaDataSource(repository, query!!)
            }.flow.cachedIn(viewModelScope)

            launch {

                medias.collectLatest { pagingData ->
                    adapterMedia.submitData(pagingData)
                }
            }
        }
    }

    //</editor-fold>
}