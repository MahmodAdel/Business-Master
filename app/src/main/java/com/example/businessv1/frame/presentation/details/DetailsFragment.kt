package com.example.businessv1.frame.presentation.details


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.BusinessDetails
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.databinding.FragmentDetailsBinding
import com.example.businessv1.frame.presentation.events.BusinessEvent
import com.example.businessv1.frame.presentation.utils.Constant
import com.example.businessv1.frame.presentation.utils.Utils
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
        _binding.textView9.setOnClickListener {
           if (Utils.checkPermission(requireActivity())){
               callPhone()
           }
        }
        _binding.ivShare.setOnClickListener {
            if (Utils.checkPermission(requireActivity())){
                shareDetails()
            }
        }


        return _binding.root
    }





    private fun callPhone() {
        if (Utils.returnPhoneNumber(_binding.textView9.text.toString()).equals(Constant.NOT_FORMATED_NUMBER)){

        }else{
            val intent= Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:" + Utils.returnPhoneNumber(_binding.textView9.text.toString())))
            startActivity(intent)
        }
    }

    fun shareDetails(){
        var appendString:StringBuilder=java.lang.StringBuilder()
        if (viewModel.dataState.value != null){
            appendString.appendln(viewModel.dataState.value!!.name)
            for (s in viewModel.dataState.value!!.categories){
                appendString.appendln(s)
            }
            appendString.appendln(viewModel.dataState.value!!.rating)
            appendString.appendln(viewModel.dataState.value!!.phone)
            appendString.appendln(viewModel.dataState.value!!.location.address1)
            appendString.appendln(viewModel.dataState.value!!.location.address1)
        }



        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, appendString.toString())
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
    }
    private fun subscribeObservers(){
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showErrorSnackBar(_binding.root, it.toString())
                viewModel.onErrorMsgDisplay()
            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding.unbind()
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                callPhone()
            } else {

            }
            return
        }
    }
}