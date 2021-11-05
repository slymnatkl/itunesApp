package com.itunesapp.view.fragments

import androidx.fragment.app.viewModels
import com.itunesapp.R
import com.itunesapp.core.extensions.formatDate
import com.itunesapp.core.fragments.BaseFragment
import com.itunesapp.databinding.FragmentHomeBinding
import com.itunesapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    //<editor-fold desc="Init Views">

    override fun initPage(){

        initViewModel()
    }

    //</editor-fold>

    //<editor-fold desc="View Model">

    private val viewModel: HomeViewModel by viewModels()

    private fun initViewModel(){

        binding.homeViewModel = viewModel
        observeViewModel()

        viewModel.getMedias()
    }

    private fun observeViewModel(){

        viewModel.loading.observe(this, { isLoading ->

            if (isLoading)
                showProgressDialog()
            else
                hideProgressDialog()
        })

        viewModel.error.observe(this, { errorResponse ->

            errorResponse.message?.let {
                showMessage(it)
            }
        })
    }

    //</editor-fold>
}