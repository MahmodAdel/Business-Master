package com.example.businessv1.frame.presentation.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.databinding.FragmentBusinessBinding
import com.example.businessv1.frame.presentation.events.BusinessEvent
import com.example.businessv1.frame.presentation.utils.OnLoadMoreListener
import com.example.businessv1.frame.presentation.utils.RecyclerViewLoadMoreScroll
import com.example.businessv1.frame.presentation.utils.SwipeHelper
import com.example.businessv1.frame.presentation.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_business.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BusinessFragment : Fragment(R.layout.fragment_business),BusinessListAdapter.Interaction{

    private val viewModel: BusinessViewModel by viewModels()
    lateinit var adapter: BusinessListAdapter
    lateinit var _binding: FragmentBusinessBinding
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: LinearLayoutManager
    private val loadingDuration: Long
        get() = (resources.getInteger(R.integer.loadingAnimDuration) / 0.8).toLong()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = this

        setAdapter()
        setRVLayoutManager()
        setRVScrollListener()

        subscribeObservers()
        viewModel.offset=0
        viewModel.setStateEvent(BusinessEvent.GetBusinessEvent,"Cairo",viewModel.offset,10)
        return _binding.root

    }



    private fun setAdapter() {
        adapter = BusinessListAdapter(interaction = this,mcontext = requireContext())
        _binding.recyclerView.adapter = adapter
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(context)
        _binding.recyclerView.layoutManager = mLayoutManager
        _binding.recyclerView.setHasFixedSize(true)
        updateRecyclerViewAnimDuration()
    }

    private  fun setRVScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        _binding.recyclerView.addOnScrollListener(scrollListener)
    }


    private fun LoadMoreData() {
        //Add the Loading View
      //  adapter.addLoadingView()
       // viewModel.offset+=10
       // viewModel.setStateEvent(BusinessEvent.GetBusinessEvent,"Cairo",viewModel.offset,10)

    }



    private fun subscribeObservers(){

        viewModel.error.observe(viewLifecycleOwner, Observer {
            _binding?.root?.let { it1 -> showErrorSnackBar(it1, it) }
            viewModel.onErrorMsgDisplay()
        })

    }
    private fun displayError(message: String?){
      //  if(message != null) text.text = message else text.text = "Unknown error."
    }

  /*  private fun showList(businessList: List<Business>){
        if(businessList.isNotEmpty()){
            if (viewModel.offset != 0){
                adapter.removeLoadingView()
                adapter.addData(businessList)
                scrollListener.setLoaded()

            }else{
                adapter.addData(businessList)
            }
        }else{
            if (viewModel.offset !=0)
                adapter.removeLoadingView()

        }
      //  adapter.submitData(viewLifecycleOwner.lifecycle, businessList)




    }*/
    private fun updateRecyclerViewAnimDuration() = _binding.recyclerView.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding.unbind()
    }

    override fun onItemSelected(
        business: Business,
        previousMoviePoster: String,
        nextMoviePoster: String
    ) {
        val action = BusinessFragmentDirections.actionBusinessFragmentToDetailsFragment(business)
        findNavController().navigate(action)    }
}