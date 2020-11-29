package com.example.businessv1.frame.presentation.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.databinding.FragmentBusinessBinding
import com.example.businessv1.databinding.FragmentDetailsBinding
import com.example.businessv1.frame.presentation.business.BusinessViewModel
import com.example.businessv1.frame.presentation.events.BusinessEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_business.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details){
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: DetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        viewModel.setStateEvent(BusinessEvent.GetBusinessEvent,args.business.id)
    }
    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Business> -> {
                    displayProgressBar(false)
                    showData(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })


    }
    private fun displayError(message: String?){
        //  if(message != null) text.text = message else text.text = "Unknown error."
    }
    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }
    private fun showData(busniss: Business) {
        _binding.apply {

            Glide.with(this@DetailsFragment)
                .load(busniss.image_url)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }
                })
                .into(this!!.imageView)

            textViewDescription.text = busniss.price


            textViewCreator.apply {
                text = "Photo by ${busniss.name}"
                paint.isUnderlineText = true
            }
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}