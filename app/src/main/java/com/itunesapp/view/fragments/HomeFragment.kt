package com.itunesapp.view.fragments

import android.app.Dialog
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.itunesapp.R
import com.itunesapp.core.fragments.BaseFragment
import com.itunesapp.databinding.FragmentHomeBinding
import com.itunesapp.repository.model.Media
import com.itunesapp.view.adapters.MediaAdapter
import com.itunesapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    //<editor-fold desc="Init Views">

    override fun initPage(){

        initViewModel()
        initAdapter()
    }

    private fun initAdapter(){

        viewModel.adapterMedia.setOnItemClickListener(object : MediaAdapter.ItemClickListener{
            override fun onItemClicked(item: Media) {
                navigateToDetail(item)
            }
        })
    }

    //</editor-fold>

    //<editor-fold desc="View Model">

    private val viewModel: HomeViewModel by viewModels()

    private fun initViewModel(){

        viewModel.setContext(requireContext())
        binding.homeViewModel = viewModel
        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.loading.observe(this, { isLoading ->

            if (isLoading)
                showProgressDialog()
            else
                hideProgressDialog()
        })

        viewModel.empty.observe(this, { isEmpty ->

            if (isEmpty) {
                binding.recyclerView.visibility = View.GONE
                binding.layoutEmpty.visibility = View.VISIBLE
            }
            else{
                binding.layoutEmpty.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })

        viewModel.error.observe(this, { errorResponse ->

            errorResponse?.message?.let {
                showMessage(it)
            }
        })
    }

    //</editor-fold>

    //<editor-fold desc="Navigate Fragments">

    private fun navigateToDetail(media: Media){

        val action = HomeFragmentDirections.actHomeToDetail(media)
        findNavController().navigate(action)
    }

    //</editor-fold>

    //<editor-fold desc="Show & Hide ProgressDialog">

    private var mSwipeRefreshBackround: Dialog? = null

    override fun showProgressDialog() {

        if (binding.swipeRefreshLayout.isRefreshing) {

            mSwipeRefreshBackround ?: run {

                mSwipeRefreshBackround = Dialog(requireContext())
                mSwipeRefreshBackround!!.setCancelable(false)
            }

            mSwipeRefreshBackround!!.show()
        }
        else
            super.showProgressDialog()
    }

    override fun hideProgressDialog() {

        if(binding.swipeRefreshLayout.isRefreshing){

            binding.swipeRefreshLayout.isRefreshing = false

            mSwipeRefreshBackround?.let {

                if(it.isShowing)
                    it.dismiss()
            }
        }
        else
            super.hideProgressDialog()
    }

    //</editor-fold>
}