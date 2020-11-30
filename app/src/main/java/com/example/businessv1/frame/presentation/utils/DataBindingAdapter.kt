package com.example.businessv1.frame.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.frame.presentation.business.BusinessAdapter
import com.example.businessv1.frame.presentation.business.BusinessListAdapter
import com.example.businessv1.frame.presentation.favorite.FavoriteAdapter
import java.util.*


@BindingAdapter("loadImageUrl")
fun loadImage(imageView: ImageView, url: String?) {

    if (!url.isNullOrBlank()) {
        Glide.with(imageView)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(imageView)
    }
}



@BindingAdapter("moviesList")
fun bindMoviesList(recyclerView: RecyclerView, list: List<Business>?) {
    list?.let { (recyclerView.adapter as FavoriteAdapter).submitList(list) }
}
