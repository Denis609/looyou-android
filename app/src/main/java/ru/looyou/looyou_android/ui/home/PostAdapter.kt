package ru.looyou.looyou_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.looyou.looyou_android.api.dto.PostDto
import ru.looyou.looyou_android.databinding.PostItemBinding
import ru.looyou.looyou_android.util.ImageLoader


class PostAdapter (private val items: List<PostDto>) : RecyclerView
.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: List<PostDto>) {
            binding.addres.text = items[adapterPosition].addres
            binding.date.text = items[adapterPosition].date
            binding.name.text = items[adapterPosition].name
            binding.years.text = items[adapterPosition].year

            val listItems = mutableListOf<String>()
            listItems.add("https://iso.500px.com/wp-content/uploads/2016/11/stock-photo-159533631-1500x1000.jpg")
            listItems.add("https://www.dianamiaus.com/wp-content/uploads/2019/07/sonnie-hiles-wy1TL6p-9rA-unsplash.jpg")
            listItems.add("https://www.paperlessmovement.com/wp-content/uploads/2019/09/o2dvsv2pnhe.jpg")
            binding.postPhotoViewPager.adapter = PostPhotoPagerAdapter(binding.root.context, listItems)
            if (listItems.size <= 1) {
                binding.tabLayout.isVisible = false
            } else {
                binding.tabLayout.setupWithViewPager(binding.postPhotoViewPager, true)
            }

            ImageLoader.picasso(
                url = items[adapterPosition].profilePhoto,
                binding.profilePhoto
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder =
        ViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items)
    }

    override fun getItemCount() = items.size
}