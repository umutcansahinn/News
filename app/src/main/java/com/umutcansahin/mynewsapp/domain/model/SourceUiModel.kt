package com.umutcansahin.mynewsapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceUiModel(
    val id: String,
    val name: String
) : Parcelable