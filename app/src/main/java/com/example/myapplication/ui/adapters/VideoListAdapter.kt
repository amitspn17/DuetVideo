package com.example.myapplication.ui.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.VideoListBinding
import com.example.myapplication.response.VideoData
import com.example.myapplication.ui.activity.UploadVideo
import kotlinx.coroutines.withContext

class VideoListAdapter :
    androidx.recyclerview.widget.ListAdapter<VideoData, VideoListAdapter.ViewHolder>(
        CategoryDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.video_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding2.data=(getItem(position))
        holder.binding2.position = position
    }

    class ViewHolder(
        private val binding: VideoListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val  binding2 =binding
        fun bind(plantings: VideoData) {
            with(binding) {
                binding.categoryName.text = plantings.video_image
                Log.d("category_name", "bind: "+plantings.video_url)
                executePendingBindings()
            }
        }
    }
   public interface ClickListener{
      fun  onclick(view: View,position: Int)
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<VideoData>() {

    override fun areItemsTheSame(
        oldItem: VideoData,
        newItem: VideoData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: VideoData,
        newItem: VideoData
    ): Boolean {
        return oldItem.id == newItem.id
    }
}