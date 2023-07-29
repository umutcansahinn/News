package com.umutcansahin.mynewsapp.ui.home_detail_screen

import com.umutcansahin.mynewsapp.databinding.FragmentHomeDetailBinding
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailFragment :
    BaseFragment<FragmentHomeDetailBinding>(FragmentHomeDetailBinding::inflate) {
    override fun observeData() {}

    override fun initView() {}


}