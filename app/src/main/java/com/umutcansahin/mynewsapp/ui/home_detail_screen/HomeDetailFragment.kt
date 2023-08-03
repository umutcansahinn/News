package com.umutcansahin.mynewsapp.ui.home_detail_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val model = args.articleUiModel

        with(binding) {
            ivArticleImage.loadImage(model.urlToImage)
            tvArticleTitle.text = model.title
            tvArticlePublishedAt.text = model.publishedAt.toFormatDate()
            tvArticleAuthor.text = model.author
            tvArticleDescription.text = model.description
            tvArticleSourceName.text = model.source.name

            btnVisitVewSite.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(model.url)
                startActivity(intent)
            }
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