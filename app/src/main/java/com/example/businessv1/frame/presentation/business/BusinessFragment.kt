package com.example.businessv1.frame.presentation.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.databinding.FragmentBusinessBinding
import com.example.businessv1.frame.presentation.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BusinessFragment : Fragment(R.layout.fragment_business),BusinessListAdapter.Interaction{

    private val viewModel: BusinessViewModel by viewModels()
    lateinit var adapter: BusinessListAdapter
    lateinit var _binding: FragmentBusinessBinding
    lateinit var mLayoutManager: LinearLayoutManager
    private val loadingDuration: Long
        get() = (resources.getInteger(R.integer.loadingAnimDuration) / 0.8).toLong()

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
        subscribeObservers()
        setupSwip()
     //   viewModel.firstFeach(BusinessEvent.GetBusinessEvent,"")
        return _binding.root

    }

    private fun setupSwip() {
        val callback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  /*  val swipedPokemonPosition = viewHolder.adapterPosition
                    val swipedPokemon: Pokemon = adapter.getPokemonAt(swipedPokemonPosition)
                    viewModel.insertPokemon(swipedPokemon)*/
                    val swipedPosition=viewHolder.adapterPosition
                    val swipedbusiness:Business = adapter.getItemPosition(swipedPosition)
                    insertFav(swipedbusiness)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        context,
                        R.string.business_add,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(_binding.recyclerView)
    }

    private fun insertFav(swipedbusiness: Business) {
       CoroutineScope(IO).launch {
           viewModel.insertBusinessFav(swipedbusiness)
       }

    }


    private fun setAdapter() {
        adapter = BusinessListAdapter(interaction = this, mcontext = requireContext())
        _binding.recyclerView.adapter = adapter
        _binding.buttonRetry.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->

            _binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            _binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            _binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            _binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    _binding.recyclerView.isVisible = false
                    _binding.textViewEmpty.isVisible = true
                } else {
                    _binding.textViewEmpty.isVisible = false
                }

        }

        setHasOptionsMenu(true)
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(context)
        _binding.recyclerView.layoutManager = mLayoutManager
        _binding.recyclerView.setHasFixedSize(true)
        updateRecyclerViewAnimDuration()
    }






    private fun subscribeObservers(){

        viewModel.error.observe(viewLifecycleOwner, Observer {
            _binding?.root?.let { it1 -> showErrorSnackBar(it1, it) }
            viewModel.onErrorMsgDisplay()
        })

        viewModel.businessList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }
    private fun updateRecyclerViewAnimDuration() = _binding.recyclerView.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
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
        findNavController().navigate(action)

    }
}