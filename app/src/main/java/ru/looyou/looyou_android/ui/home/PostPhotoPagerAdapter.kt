package ru.looyou.looyou_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.looyou.looyou_android.databinding.PostPhotoItemBinding
import ru.looyou.looyou_android.util.ImageLoader


class PostPhotoPagerAdapter internal constructor(private val items: List<String>) :
    RecyclerView.Adapter<PostPhotoPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostPhotoPagerAdapter.ViewHolder =
        ViewHolder(
            PostPhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: PostPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: List<String>) {
            ImageLoader.picasso(
                url = items[adapterPosition],
                imageView = binding.postPhoto
            )
        }
    }
}