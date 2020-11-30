package com.example.businessv1.frame.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.databinding.FragmentBusinessBinding
import com.example.businessv1.databinding.FragmentFavoriteBinding
import com.example.businessv1.frame.presentation.business.BusinessFragmentDirections
import com.example.businessv1.frame.presentation.business.BusinessListAdapter
import com.example.businessv1.frame.presentation.business.BusinessViewModel
import com.example.businessv1.frame.presentation.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment: Fragment(R.layout.fragment_favorite),FavoriteAdapter.Interaction{
    private val viewModel: BusinessViewModel by viewModels()
    lateinit var adapter: FavoriteAdapter
    lateinit var _binding: FragmentFavoriteBinding
    lateinit var mLayoutManager: LinearLayoutManager
    private val loadingDuration: Long
        get() = (resources.getInteger(R.integer.loadingAnimDuration) / 0.8).toLong()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = this
        viewModel.getFavoritList()
        setUpRecyclerView()
        setupSwip()

        viewModel.error.observe(viewLifecycleOwner, {
            showErrorSnackBar(_binding.root, it)
            viewModel.onErrorMsgDisplay()
        })

        return _binding.root
    }
    private fun updateRecyclerViewAnimDuration() = _binding.recyclerView.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
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

                    val swipedPosition=viewHolder.adapterPosition
                    val swipedbusiness:Business = adapter.getItemPosition(swipedPosition)
                    removeFav(swipedbusiness)
                    viewModel.getFavoritList()
                    Toast.makeText(
                        context,
                        R.string.business_removed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(_binding.recyclerView)
    }
    private fun removeFav(swipedbusiness: Business) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteBusinessFav(swipedbusiness)
        }

    }

    private fun setUpRecyclerView() {

        //Setup recyclerView
        context?.let {
            mLayoutManager = LinearLayoutManager(context)
            _binding.recyclerView.layoutManager = mLayoutManager
            _binding.recyclerView.setHasFixedSize(true)
            updateRecyclerViewAnimDuration()
        }



        adapter = FavoriteAdapter(interaction = this)
        _binding.recyclerView.adapter = adapter
    }

    override fun onItemSelected(
        business: Business,
        previousMoviePoster: String,
        nextMoviePoster: String
    ) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(business)
        findNavController().navigate(action)
    }

}