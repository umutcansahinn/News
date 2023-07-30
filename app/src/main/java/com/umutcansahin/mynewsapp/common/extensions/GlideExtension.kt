package com.umutcansahin.mynewsapp.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .fitCenter()
        .into(this)
}