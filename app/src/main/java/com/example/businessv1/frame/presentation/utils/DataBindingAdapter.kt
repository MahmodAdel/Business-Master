package com.example.businessv1.frame.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.frame.presentation.business.BusinessAdapter
import com.example.businessv1.frame.presentation.business.BusinessListAdapter
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

/*
@BindingAdapter("categoryList")
fun bindActorList(recyclerView: RecyclerView, list: List<Locale.Category>?) {
    list?.let { recyclerView.adapter = ActorsAdapter(it) }
}*/

@BindingAdapter("businessList")
fun bindBusinessList(recyclerView: RecyclerView, dataState: DataState<List<Business>>?) {
    when(dataState){
        is DataState.Success<List<Business>> -> {
            (recyclerView.adapter as BusinessListAdapter).submitList(dataState.data)
        }
    }
}
