package com.tymeglobal.test.currency_converter.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tymeglobal.test.currency_converter.R

object ImageUtils {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        if (url.isNullOrEmpty()) {
            view.setImageResource(R.drawable.ic_image_default)
        } else {
            Glide.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_image_default)
                .error(R.drawable.ic_image_default)
                .into(view)
        }
    }
}