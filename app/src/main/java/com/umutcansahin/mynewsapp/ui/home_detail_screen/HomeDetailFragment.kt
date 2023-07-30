package com.umutcansahin.mynewsapp.ui.home_detail_screen

import androidx.navigation.fragment.navArgs
import com.umutcansahin.mynewsapp.common.extensions.loadImage
import com.umutcansahin.mynewsapp.common.extensions.toFormatDate
import com.umutcansahin.mynewsapp.databinding.FragmentHomeDetailBinding
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


}