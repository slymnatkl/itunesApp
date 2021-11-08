package com.itunesapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.itunesapp.R
import com.itunesapp.core.components.EntityFilterPicker
import com.itunesapp.repository.model.Media
import com.itunesapp.repository.model.MediaType
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

    @SuppressLint("StaticFieldLeak")
    private var context : Context? = null

    val empty = MutableLiveData<Boolean>()

    private lateinit var medias: Flow<PagingData<Media>>
    val adapterMedia = MediaAdapter()

    private var qTerm: String? = null
    private var qMedia: MediaType = MediaType.ALL

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
            fetchDelayed(false)

            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {

            this@HomeViewModel.qTerm = query
            searchJob?.cancel()
            fetchDelayed(false)

            return true
        }
    }

    val entityFilterPickerListener = object : EntityFilterPicker.EntityFilterPickerListener{

        override fun onFilteredByMovies() {
            qMedia = MediaType.MOVIES
            fetchDelayed(true)
        }

        override fun onFilteredByMusic() {
            qMedia = MediaType.MUSIC
            fetchDelayed(true)
        }

        override fun onFilteredByApps() {
            qMedia = MediaType.APPS
            fetchDelayed(true)
        }

        override fun onFilteredByBooks() {
            qMedia = MediaType.BOOKS
            fetchDelayed(true)
        }

        override fun onAllFiltersRemoved() {
            qMedia = MediaType.ALL
            fetchDelayed(true)
        }
    }

    private fun fetchDelayed(showEmpty: Boolean){

        if((!qTerm.isNullOrEmpty() && qTerm!!.length >= 2)){

            searchJob = launch {

                delay(700)
                refreshPage()
            }
        }
        else if(showEmpty){
            empty.value = true
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
                MediaDataSource(repository, qTerm!!, qMedia.value)
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