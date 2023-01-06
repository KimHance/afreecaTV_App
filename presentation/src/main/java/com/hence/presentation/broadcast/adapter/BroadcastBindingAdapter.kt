package com.hence.presentation.broadcast.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("bindThumbnail")
fun AppCompatImageView.bindThumbnail(thumb: String?) {
    thumb?.let {
        Glide.with(this).load("https:$it.jpg").transform(CenterInside(), RoundedCorners(20))
            .into(this)
    }

}

@BindingAdapter("bindProfile")
fun AppCompatImageView.bindProfile(profile: String?) {
    profile?.let {
        Glide.with(this).load("https:$it").circleCrop().into(this)
    }
}