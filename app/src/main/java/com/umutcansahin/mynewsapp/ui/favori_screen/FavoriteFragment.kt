package com.umutcansahin.mynewsapp.ui.favori_screen

import android.os.Bundle
import android.view.View
import com.umutcansahin.mynewsapp.databinding.FragmentFavoriteBinding
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}