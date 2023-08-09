package com.umutcansahin.mynewsapp.common.extensions

infix fun String.okWith(bound: Int) = length >= bound

val String.Companion.EMPTY: String by lazy { "" }