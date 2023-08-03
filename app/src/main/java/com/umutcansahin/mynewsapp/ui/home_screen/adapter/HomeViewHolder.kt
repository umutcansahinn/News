package com.umutcansahin.mynewsapp.ui.home_screen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.umutcansahin.mynewsapp.common.extensions.loadImage
import com.umutcansahin.mynewsapp.common.extensions.toFormatDate
import com.umutcansahin.mynewsapp.databinding.HomeAdapterItemBinding
import com.umutcansahin.mynewsapp.domain.model.ArticleUiModel

class HomeViewHolder(
    private val binding: HomeAdapterItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(model: ArticleUiModel, onClick: (ArticleUiModel) -> Unit) {
        with(binding) {
            ivArticleImage.loadImage(model.urlToImage)
            tvArticleTitle.text = model.title
            tvArticlePublishedAt.text = model.publishedAt.toFormatDate()
            root.setOnClickListener {
                onClick.invoke(model)
            }
        }
    }
}