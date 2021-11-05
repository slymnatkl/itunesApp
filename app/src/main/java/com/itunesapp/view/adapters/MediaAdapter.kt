package com.itunesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itunesapp.databinding.RowItemMediaGridBinding
import com.itunesapp.repository.model.Media
import javax.inject.Inject

class MediaAdapter @Inject constructor(): RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {

    var itemList: List<Media> = ArrayList()

    fun setItems(itemList: List<Media>){

        this@MediaAdapter.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = RowItemMediaGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {

        holder.binding.item = itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MediaViewHolder(val binding: RowItemMediaGridBinding): RecyclerView.ViewHolder(binding.root)
}