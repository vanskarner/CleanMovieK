package com.vanskarner.cleanmoviek.ui

import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.vanskarner.cleanmoviek.R

@BindingAdapter("imageUrl")
fun loadImageUrl(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_movie_24)
        .error(R.drawable.ic_error_image_24)
        .into(imageView)
}

@BindingAdapter("imageBase64")
fun loadImageBase64(imageView: ImageView, imageBase64: String?) {
    Glide.with(imageView.context)
        .asBitmap()
        .load(Base64.decode(imageBase64, Base64.DEFAULT))
        .placeholder(R.drawable.ic_movie_24)
        .into(imageView)
}

@BindingAdapter("imageDetailUrl")
fun loadImageDetailUrl(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_movie_24)
        .error(R.drawable.ic_movie_background)
        .into(imageView)
}

@BindingAdapter("imageDetailBase64")
fun loadImageDetailBase64(imageView: ImageView, imageBase64: String?) {
    Glide.with(imageView.context)
        .asBitmap()
        .load(Base64.decode(imageBase64, Base64.DEFAULT))
        .placeholder(R.drawable.ic_movie_background)
        .into(imageView)
}