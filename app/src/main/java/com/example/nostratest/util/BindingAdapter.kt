package com.example.nostratest.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nostratest.R


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("glideImage")
    fun imageArticle(imageView: ImageView, imgUrl:String?){
        imgUrl?.let {
            Glide.with(imageView.context)
                .load(it)
                .fitCenter()
                .apply(RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_image)
                .into(imageView)
        }
    }

}