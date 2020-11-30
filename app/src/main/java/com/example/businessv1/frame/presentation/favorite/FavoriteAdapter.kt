package com.example.businessv1.frame.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.databinding.ItemFavListBinding
import com.example.businessv1.databinding.ItemListBinding


class FavoriteAdapter(
    private val interaction: Interaction? = null
) : ListAdapter<Business, FavoriteAdapter.MovieViewHolder>(MovieDiffCallback()) {


    class MovieDiffCallback : DiffUtil.ItemCallback<Business>() {
        override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemFavListBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return MovieViewHolder(binding, interaction)
    }
    fun getItemPosition(position: Int):Business{
        return getItem(position)!!
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        val previousMovie = if (position == 0) getItem(itemCount - 1) else getItem(position - 1)
        val nextMovie = if (position == itemCount - 1) getItem(0) else getItem(position + 1)

        holder.bind(movie, previousMovie, nextMovie)
    }



    class MovieViewHolder
    constructor(
        private val binding: ItemFavListBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(movie: Business, previousMovie: Business, nextMovie: Business) = with(itemView) {
            binding.business = movie
            binding.executePendingBindings()

            //Notify the listener on movie item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(movie, previousMovie.id, nextMovie.id)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(movie: Business, previousMoviePoster: String, nextMoviePoster: String)
    }
}

