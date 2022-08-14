package ru.looyou.looyou_android.util

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.looyou.looyou_android.R

object ImageLoader {
    fun picasso(url: String?, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .fit().centerCrop()
            .placeholder(R.drawable.ic_profile)
            .into(imageView)
    }
}