package com.umutcansahin.mynewsapp.ui.home_screen

import com.umutcansahin.mynewsapp.databinding.FragmentHomeBinding
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun observeData() {}

    override fun initView() {}

}