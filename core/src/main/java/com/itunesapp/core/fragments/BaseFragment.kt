package com.itunesapp.core.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.itunesapp.core.activities.BaseActivity
import com.itunesapp.core.extensions.showMessage

abstract class BaseFragment<VDBinding : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment()
{
    lateinit var binding: VDBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPage()
    }

    abstract fun initPage()

    protected fun showMessage(message: String){
        (activity as BaseActivity<*>).showMessage(message)
    }

    open fun showProgressDialog(){
        (activity as BaseActivity<*>).showProgressDialog()
    }

    open fun hideProgressDialog(){
        (activity as BaseActivity<*>).hideProgressDialog()
    }
}