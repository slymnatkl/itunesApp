package com.itunesapp.viewmodel

import android.content.Context
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.itunesapp.R
import com.itunesapp.core.components.EntityFilterPicker
import com.itunesapp.repository.model.EntityType
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
    private var qTerm: String? = null
    private var qEntity: EntityType = EntityType.NONE
    val adapterMedia = MediaAdapter()

    val empty = MutableLiveData<Boolean>()

    //</editor-fold>

    //<editor-fold desc="Init Views">

    fun setContext(context: Context){

        this@HomeViewModel.context = context
    }

    init {
        launch {

            adapterMedia.addLoadStateListener {

                empty.value = adapterMedia.itemCount == 0
            }

            adapterMedia.loadStateFlow.collectLatest { loadStates ->

                loading.value = (loadStates.refresh is LoadState.Loading)

                if(loadStates.refresh is LoadState.Error)
                    error.value = ErrorResponse((loadStates.refresh as LoadState.Error).error.localizedMessage)
                else
                    error.value = null
            }

            empty.value = true
        }
    }

    //</editor-fold>

    //<editor-fold desc="Search & Filter Query">

    private var searchJob: Job? = null

    val searchQueryChangeListener = object : SearchView.OnQueryTextListener{

        override fun onQueryTextSubmit(query: String?): Boolean {

            this@HomeViewModel.qTerm = query

            searchJob?.cancel()
            refreshPage()

            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {

            this@HomeViewModel.qTerm = query

            searchJob?.cancel()

            if (!query.isNullOrEmpty() && query.length >= 2)
                filterDelayed()

            return true
        }
    }

    val entityFilterPickerListener = object : EntityFilterPicker.EntityFilterPickerListener{

        override fun onFilteredByMovies() {
            qEntity = EntityType.MOVIES
            filterDelayed()
        }

        override fun onFilteredByMusic() {
            qEntity = EntityType.MUSIC
            filterDelayed()
        }

        override fun onFilteredByApps() {
            qEntity = EntityType.APPS
            filterDelayed()
        }

        override fun onFilteredByBooks() {
            qEntity = EntityType.BOOKS
            filterDelayed()
        }

        override fun onAllFiltersRemoved() {
            qEntity = EntityType.NONE
            filterDelayed()
        }
    }

    private fun filterDelayed(){

        searchJob = launch {

            delay(700)
            refreshPage()
        }
    }

    fun refreshPage(){

        if (!qTerm.isNullOrEmpty()){

            fetchMedias()
        }
        else{
            loading.value = false
            context?.let {
                error.value = ErrorResponse(it.getString(R.string.empty_text))
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="Fetch Data">

    private fun fetchMedias(){

        qTerm?.let {

            medias = Pager(PagingConfig(pageSize = MediaDataSource.LIMIT)){
                MediaDataSource(
                    repository,
                    qTerm!!,
                    qEntity.value
                )
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