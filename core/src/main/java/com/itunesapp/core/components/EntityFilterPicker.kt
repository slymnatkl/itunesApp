package com.itunesapp.core.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.itunesapp.core.R
import com.itunesapp.core.databinding.ComponentEntityFilterPickerBinding

class EntityFilterPicker: FrameLayout {

    private lateinit var binding: ComponentEntityFilterPickerBinding

    constructor(context: Context) : super(context){
        initLayout()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initLayout()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initLayout()
    }

    private fun initLayout(){

        binding = ComponentEntityFilterPickerBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        initButtonToggleGroup()
    }

    private fun initButtonToggleGroup(){

        binding.btnToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->

            if (isChecked){

                when(checkedId){
                    R.id.btnMovies -> listener?.onFilteredByMovies()
                    R.id.btnMusic -> listener?.onFilteredByMusic()
                    R.id.btnApps -> listener?.onFilteredByApps()
                    R.id.btnBooks -> listener?.onFilteredByBooks()
                }
            }
            else
                listener?.onAllFiltersRemoved()
        }
    }

    //<editor-fold desc="Listener">

    private var listener: EntityFilterPickerListener? = null

    fun setOnEntityFilterPickerListener(listener: EntityFilterPickerListener?){

        this@EntityFilterPicker.listener = listener
    }

    interface EntityFilterPickerListener{
        fun onFilteredByMovies()
        fun onFilteredByMusic()
        fun onFilteredByApps()
        fun onFilteredByBooks()
        fun onAllFiltersRemoved()
    }

    //</editor-fold>
}