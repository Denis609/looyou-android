package ru.looyou.looyou_android.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import ru.looyou.looyou_android.databinding.PostPhotoItemBinding
import ru.looyou.looyou_android.util.ImageLoader

class PostPhotoPagerAdapter(
    val context: Context,
    private val items: List<String>
) : PagerAdapter() {
    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = PostPhotoItemBinding.inflate(LayoutInflater.from(context), container, false)
        ImageLoader.picasso(
            url = items[position],
            binding.postPhoto
        )
        container.addView(binding.root)
        return binding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}


