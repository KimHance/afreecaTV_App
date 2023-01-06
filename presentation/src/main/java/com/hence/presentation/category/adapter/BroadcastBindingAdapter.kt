package com.hence.presentation.category.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("bindThumbnail")
fun AppCompatImageView.bindThumbnail(thumb: String) {
    Glide.with(this).load("https:$thumb.jpg").transform(CenterInside(),RoundedCorners(20)).into(this)
}

@BindingAdapter("bindProfile")
fun AppCompatImageView.bindProfile(profile: String) {
    Glide.with(this).load("https:$profile").circleCrop().into(this)
}