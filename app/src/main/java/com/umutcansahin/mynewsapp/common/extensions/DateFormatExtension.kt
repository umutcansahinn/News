package com.umutcansahin.mynewsapp.common.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toFormatDate(): String {
    //"dd MMM yyyy / HH:mm"
    //"yyyy-MM-dd'/'HH:mm"
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormatter = SimpleDateFormat("dd MMM yyyy / HH:mm")
    val date = inputFormatter.parse(this) ?: "-"
    return outputFormatter.format(date)
}