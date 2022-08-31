package ru.looyou.looyou_android.ui.home

import android.annotation.SuppressLint
import android.view.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.databinding.PostItemBinding
import ru.looyou.looyou_android.ui.home.search.PostDto
import ru.looyou.looyou_android.util.ImageLoader


class PostAdapter(private val items: List<PostDto>) : RecyclerView
.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(items: List<PostDto>) {
            binding.addres.text = items[adapterPosition].addres
            binding.date.text = items[adapterPosition].date
            binding.name.text = items[adapterPosition].name
            binding.years.text = items[adapterPosition].year

            val listItems = mutableListOf<String>()
            listItems.add("https://s.ws.pho.to/76eeee/img/index/ai/source.jpg")
            listItems.add("https://www.dianamiaus.com/wp-content/uploads/2019/07/sonnie-hiles-wy1TL6p-9rA-unsplash.jpg")
            listItems.add("https://www.paperlessmovement.com/wp-content/uploads/2019/09/o2dvsv2pnhe.jpg")
            binding.postPhotoViewPager.adapter = PostPhotoPagerAdapter(listItems)
            binding.postPhotoViewPager.isUserInputEnabled = false

            val maxCountPhoto: Int = items.size - 1
            val minCountPhoto: Int = 0
            var countPhoto: Int = 0

            binding.postPhotoViewPager.setOnTouchListener() { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    if (motionEvent.x > binding.postPhotoViewPager.width / 2) {
                        // право
                        if (countPhoto >= maxCountPhoto) {
                            countPhoto = minCountPhoto
                        } else {
                            countPhoto += 1
                        }
                        binding.postPhotoViewPager.currentItem = countPhoto
                    } else {
                        // лево
                        if (countPhoto <= minCountPhoto) {
                            countPhoto = maxCountPhoto
                        } else {
                            countPhoto -= 1
                        }
                        binding.postPhotoViewPager.currentItem = countPhoto
                    }
                }
                true
            }

            if (listItems.size <= 1) {
                binding.tabLayout.isVisible = false
            } else {
                TabLayoutMediator(binding.tabLayout, binding.postPhotoViewPager) { tab, position ->

                }.attach()
            }

            ImageLoader.picasso(
                url = items[adapterPosition].profilePhoto,
                binding.profilePhoto
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
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