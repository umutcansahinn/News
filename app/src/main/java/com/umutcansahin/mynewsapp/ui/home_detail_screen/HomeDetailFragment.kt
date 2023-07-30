package com.umutcansahin.mynewsapp.ui.home_detail_screen

import androidx.navigation.fragment.navArgs
import com.umutcansahin.mynewsapp.common.extensions.loadImage
import com.umutcansahin.mynewsapp.common.extensions.toFormatDate
import com.umutcansahin.mynewsapp.databinding.FragmentHomeDetailBinding
import com.umutcansahin.mynewsapp.ui.MainActivity
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailFragment :
    BaseFragment<FragmentHomeDetailBinding>(FragmentHomeDetailBinding::inflate) {

    private val args: HomeDetailFragmentArgs by navArgs()
    override fun observeData() {}

    override fun initView() {
        val model = args.articleUiModel

        with(binding) {
            ivArticleImage.loadImage(model.urlToImage)
            tvArticleTitle.text = model.title
            tvArticlePublishedAt.text = model.publishedAt.toFormatDate()
            tvArticleAuthor.text = model.author
            tvArticleDescription.text = model.description
            tvArticleSourceName.text= model.source.name
        }
    }

    private fun hideBottomBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.hideBottomNavigation()
        }
    }

    private fun showBottomBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.showBottomNavigation()
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomBar()
    }

    override fun onPause() {
        super.onPause()
        showBottomBar()
    }


}