package com.example.businessv1.frame.presentation.business

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bindColor
import bindDimen
import blendColors
import com.example.businessv1.R

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.databinding.ItemListBinding
import com.example.businessv1.databinding.ProgressLoadingBinding
import com.example.businessv1.frame.presentation.utils.Constant
import dp
import getValueAnimator
import screenWidth

 class BusinessListAdapter(
    private val interaction: Interaction? = null
,mcontext:Context) : ListAdapter<Business, RecyclerView.ViewHolder>(MovieDiffCallback()) {


    private val originalBg: Int by bindColor(mcontext!!, R.color.list_item_bg_collapsed)
    private val expandedBg: Int by bindColor(mcontext!!, R.color.list_item_bg_expanded)

    private val listItemHorizontalPadding: Float by bindDimen(mcontext!!, R.dimen.list_item_horizontal_padding)
    private val listItemVerticalPadding: Float by bindDimen(mcontext!!, R.dimen.list_item_vertical_padding)
    private var originalHeight = -1 // will be calculated dynamically
    private var expandedHeight = -1 // will be calculated dynamically
    private val originalWidth = mcontext!!.screenWidth - 48.dp
    private val expandedWidth = mcontext!!.screenWidth - 24.dp

    private val listItemExpandDuration: Long get() = (300L / 0.8).toLong()


    private var expandedModel: Business? = null
    private var isScaledDown = false
    private lateinit var recyclerView: RecyclerView

    class MovieDiffCallback : DiffUtil.ItemCallback<Business>() {
        override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem == newItem
        }
    }
    fun addData(dataViews: List<Business>) {
        addData(dataViews)
       // notifyDataSetChanged()
    }
    /* fun removeLoadingView() {
         //Remove loading item
         if (businessList.size != 0) {
             businessList.removeAt(businessList.size - 1)
             notifyItemRemoved(businessList.size)
         }
     }
     fun addLoadingView() {
         //add loading item
         Handler().post {
             Items(null)
             notifyItemInserted(getItems.size - 1)
         }
     }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val layoutInflater = LayoutInflater.from(parent.context)

            val binding = ItemListBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return BusinessViewHolder(binding, interaction)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)

            val binding = ProgressLoadingBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.progressbar.indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                binding.progressbar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            }
            return LoadingViewHolder(binding, interaction)
        }
    }
    class LoadingViewHolder
    constructor(
        private val binding: ProgressLoadingBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType ==  Constant.VIEW_TYPE_ITEM) {
            val movie = getItem(position)
            val previousMovie = if (position == 0) getItem(itemCount - 1) else getItem(position - 1)
            val nextMovie = if (position == itemCount - 1) getItem(0) else getItem(position + 1)
            val businessHolder: BusinessViewHolder = holder as BusinessViewHolder
            businessHolder.bind(movie, previousMovie, nextMovie)
        }
    }
     inner class BusinessViewHolder constructor(
        private val binding: ItemListBinding,
        private val interaction: Interaction?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(business: Business, previousMovie: Business, nextMovie: Business) = with(itemView) {
            binding.business = business
            binding.executePendingBindings()
            expandItem(binding, business == expandedModel, animate = false)
            scaleDownItem(binding, position, isScaledDown)
            binding.chevron.setOnClickListener {
                if (expandedModel == null) {

                    // expand clicked view
                    expandItem(binding, expand = true, animate = true)
                    expandedModel = business
                } else if (expandedModel == business) {

                    // collapse clicked view
                    expandItem(binding, expand = false, animate = true)
                    expandedModel = null
                }
            }
            //Notify the listener on movie item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(business, previousMovie.id, nextMovie.id)
            }
        }

    }
    private fun scaleDownItem(
        position1: ItemListBinding,
        position: Int,
        isScaleDown: Boolean
    ) {
        setScaleDownProgress(position1, position, if (isScaleDown) 1f else 0f)
    }
    private fun setScaleDownProgress(
        position1: ItemListBinding,
        position: Int,
        progress: Float
    ) {

        val itemExpanded = position >= 0 && getItem(position) == expandedModel
        position1.cardContainer.layoutParams.apply {
            width = ((if (itemExpanded) expandedWidth else originalWidth) * (1 - 0.1f * progress)).toInt()
            height = ((if (itemExpanded) expandedHeight else originalHeight) * (1 - 0.1f * progress)).toInt()
//            log("width=$width, height=$height [${"%.2f".format(progress)}]")
        }
        position1.cardContainer.requestLayout()

        position1.scaleContainer.scaleX = 1 - 0.05f * progress
        position1.scaleContainer.scaleY = 1 - 0.05f * progress

        position1.scaleContainer.setPadding(
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt()
        )

        position1.listItemFg.alpha = progress
    }
    private fun expandItem(
        expand1: ItemListBinding,
        expand: Boolean,
        animate: Boolean
    ) {
        if (animate) {
            val animator = getValueAnimator(
                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) { progress -> setExpandProgress(expand1, progress) }

            if (expand) animator.doOnStart { expand1.expandView.isVisible = true }
            else animator.doOnEnd { expand1.expandView.isVisible = false }

            animator.start()
        } else {

            // show expandView only if we have expandedHeight (onViewAttached)
            expand1.expandView.isVisible = expand && expandedHeight >= 0
            setExpandProgress(expand1, if (expand) 1f else 0f)
        }
    }
    private fun setExpandProgress(
        progress1: ItemListBinding,
        progress: Float
    ) {
        if (expandedHeight > 0 && originalHeight > 0) {
            progress1.cardContainer.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        progress1.cardContainer.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

        progress1.cardContainer.setBackgroundColor(blendColors(originalBg, expandedBg, progress))
        progress1.cardContainer.requestLayout()

        progress1.chevron.rotation = 90 * progress
    }
    interface Interaction {
        fun onItemSelected(movie: Business, previousMoviePoster: String, nextMoviePoster: String)
    }
}