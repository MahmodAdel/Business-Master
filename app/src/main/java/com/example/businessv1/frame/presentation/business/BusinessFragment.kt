package com.example.businessv1.frame.presentation.business

import android.os.Bundle
import android.view.View
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_business.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BusinessFragment : Fragment(R.layout.fragment_business),BusinessAdapter.OnItemClickListener{

    lateinit var  adapter: BusinessAdapter
    private val viewModel: BusinessViewModel by viewModels()
    private var _binding: FragmentBusinessBinding? = null
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: LinearLayoutManager
    private val loadingDuration: Long
        get() = (resources.getInteger(R.integer.loadingAnimDuration) / 0.8).toLong()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBusinessBinding.bind(view)
//** Set the adapterLinear of the RecyclerView
        setAdapter()
        //** Set the Layout Manager of the RecyclerView
        setRVLayoutManager()

        //** Set the scrollListerner of the RecyclerView
        setRVScrollListener()

        setSwipInit()

        subscribeObservers()
        viewModel.offset=0
        viewModel.setStateEvent(BusinessEvent.GetBusinessEvent,"Cairo",viewModel.offset,10)
    }

    private fun setSwipInit() {
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(recycler_view) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val markAsUnreadButton = markAsUnreadButton(position)
                val archiveButton = archiveButton(position)
                when (position) {
                    1 -> buttons = listOf(deleteButton)
                    2 -> buttons = listOf(deleteButton, markAsUnreadButton)
                    3 -> buttons = listOf(deleteButton, markAsUnreadButton, archiveButton)
                    else -> Unit
                }
                return buttons
            }
        })

        itemTouchHelper.attachToRecyclerView(recycler_view)
    }
    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                  //  toast("Deleted item $position")
                }
            })
    }

    private fun markAsUnreadButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Mark as unread",
            14.0f,
            android.R.color.holo_green_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                   // toast("Marked as unread item $position")
                }
            })
    }

    private fun archiveButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Archive",
            14.0f,
            android.R.color.holo_blue_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                   // toast("Archived item $position")
                }
            })
    }

    private fun setAdapter() {
        adapter = BusinessAdapter(context,this)
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.setHasFixedSize(true)
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
        recycler_view.addOnScrollListener(scrollListener)
    }
    private fun LoadMoreData() {
        //Add the Loading View
        adapter.addLoadingView()
        viewModel.offset+=10
        viewModel.setStateEvent(BusinessEvent.GetBusinessEvent,"Cairo",viewModel.offset,10)

    }



    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<Business>> -> {
                    displayProgressBar(false)
                    showList(dataState.data)
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

    private fun showList(businessList: List<Business>){
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




    }
    private fun updateRecyclerViewAnimDuration() = recycler_view.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onItemClick(business: Business) {
        val action = BusinessFragmentDirections.actionBusinessFragmentToDetailsFragment(business)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}