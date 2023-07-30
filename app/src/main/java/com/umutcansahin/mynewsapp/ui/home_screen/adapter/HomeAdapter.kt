package com.umutcansahin.mynewsapp.ui.home_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.umutcansahin.mynewsapp.databinding.HomeAdapterItemBinding
import com.umutcansahin.mynewsapp.domain.model.ArticleUiModel

class HomeAdapter(
    private val onClick:(ArticleUiModel)->Unit
) : ListAdapter<ArticleUiModel, HomeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let { model ->
            holder.onBind(model = model, onClick = onClick)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleUiModel>() {
            override fun areItemsTheSame(
                oldItem: ArticleUiModel,
                newItem: ArticleUiModel
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: ArticleUiModel,
                newItem: ArticleUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}