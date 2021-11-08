package com.itunesapp.view.fragments

import com.itunesapp.R
import com.itunesapp.core.fragments.BaseFragment
import com.itunesapp.databinding.FragmentDetailBinding
import com.itunesapp.repository.model.Media
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private var media: Media? = null

    //<editor-fold desc="Init Views">

    override fun initPage(){

        getBundle()
        binding.item = media
    }

    private fun getBundle(){

        arguments?.let {
            media = DetailFragmentArgs.fromBundle(it).media
        }
    }

    //</editor-fold>
}