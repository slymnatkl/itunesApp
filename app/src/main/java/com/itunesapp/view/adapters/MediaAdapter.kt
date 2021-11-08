package com.itunesapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itunesapp.databinding.RowItemMediaGridBinding
import com.itunesapp.repository.model.Media

class MediaAdapter : PagingDataAdapter<Media, MediaAdapter.MediaViewHolder>(DiffUtilCallBack()) {

    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = RowItemMediaGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.binding.item = getItem(position)
        holder.binding.mediaItemClick = object : ItemClickListener{
            override fun onItemClicked(item: Media) {

                clickListener?.onItemClicked(item)
            }
        }
    }

    class MediaViewHolder(val binding: RowItemMediaGridBinding): RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickListener(clickListener: ItemClickListener){
        this@MediaAdapter.clickListener = clickListener
    }

    interface ItemClickListener {
        fun onItemClicked(item: Media)
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<Media>() {

        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.collectionId == newItem.collectionId
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.equals(newItem);
        }
    }
}