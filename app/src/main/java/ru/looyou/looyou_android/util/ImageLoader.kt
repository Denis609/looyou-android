package ru.looyou.looyou_android.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoader {
    fun picasso(url: String?, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .fit().centerCrop()
            .into(imageView)
    }
}