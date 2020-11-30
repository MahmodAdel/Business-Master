package com.example.businessv1.frame.presentation.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.example.businessv1.R
import com.example.businessv1.databinding.FragmentDetailsBinding

import com.example.businessv1.frame.presentation.events.BusinessEvent
import com.example.businessv1.frame.presentation.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details){
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: DetailsViewModel by viewModels()
    lateinit var _binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = this
        viewModel.setStateEvent(BusinessEvent.GetBusinessEvent,args.business.id)


        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
    }
    private fun subscribeObservers(){
        viewModel.error.observe(viewLifecycleOwner, Observer {
            showErrorSnackBar(_binding.root, it)
            viewModel.onErrorMsgDisplay()
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding.unbind()
    }
}