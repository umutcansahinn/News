package com.umutcansahin.mynewsapp.common.extensions

import android.view.RoundedCorner
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.umutcansahin.mynewsapp.R
import com.umutcansahin.mynewsapp.common.Constants.ROUNDED_CORNERS

val loadingOptions = RequestOptions()
    .placeholder(R.drawable.ic_baseline_image)
    .error(R.drawable.ic_broken_image)
    .centerCrop()
    .transform(RoundedCorners(ROUNDED_CORNERS))


val crossFadeFactory = DrawableCrossFadeFactory
    .Builder()
    .setCrossFadeEnabled(true)
    .build()

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .apply(loadingOptions)
        .transition(DrawableTransitionOptions.withCrossFade(crossFadeFactory))
        .into(this)
}