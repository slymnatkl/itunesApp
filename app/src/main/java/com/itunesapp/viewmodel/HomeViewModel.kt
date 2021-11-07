package com.itunesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.itunesapp.repository.model.Media
import com.itunesapp.repository.network.repository.Repository
import com.itunesapp.repository.network.response.ErrorResponse
import com.itunesapp.view.adapters.MediaAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): BaseViewModel(){

    val medias = MutableLiveData<List<Media>>()
    val adapterMedia = MediaAdapter()

    fun getMedias(){

        launch {

            loading.value = true

            try {
                val mediasResult = repository.getMedias("Matrix", 20, 0)
                medias.value = mediasResult.results
                this@HomeViewModel.adapterMedia.setItems(medias.value!!)
                loading.value = false
            }
            catch (exception: Exception){
                loading.value = false
                error.value = ErrorResponse(exception.message)
            }
        }
    }
}