package com.example.nostratest.presentation.list_headlines.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nostratest.R
import com.example.nostratest.databinding.ItemNewsBinding
import com.example.nostratest.domain.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    private val listArticle = ArrayList<Article>()
    var onLoadMore: (() -> Unit)? = null
    var onClickItem: ((Article) -> Unit)? = null

    fun setData(list: List<Article>) {
        if (list == null) return
        listArticle.clear()
        listArticle.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        listArticle.clear()
        notifyDataSetChanged()
    }

    fun updateData(list: List<Article>) {
        val insertIndex = listArticle.size - 1
        listArticle.addAll(insertIndex, list)
        notifyItemRangeInserted(insertIndex, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentData = listArticle[position]
        holder.bind(currentData)
        if ((position >= getItemCount() - 1))
            onLoadMore?.invoke()
    }

    override fun getItemCount() = listArticle.size


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemNewsBinding.bind(itemView)
        fun bind(data: Article){
            binding.article = data
            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }
        }
    }


}