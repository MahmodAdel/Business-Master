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
import androidx.recyclerview.widget.RecyclerView
import bindColor
import bindDimen
import blendColors
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.businessv1.R
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.databinding.ItemListBinding
import com.example.businessv1.databinding.ProgressLoadingBinding
import com.example.businessv1.frame.presentation.utils.Constant
import dp
import getValueAnimator
import screenWidth

class BusinessAdapter(mcontext: Context?, private val listener: BusinessAdapter.OnItemClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val businessList: ArrayList<Business?> = ArrayList()
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

    override fun getItemCount(): Int =businessList.size
    class LoadingViewHolder(binding: ProgressLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    fun addData(dataViews: List<Business>) {
        this.businessList.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Business? {
        return businessList[position]
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            businessList.add(null)
            notifyItemInserted(businessList.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (businessList.size != 0) {
            businessList.removeAt(businessList.size - 1)
            notifyItemRemoved(businessList.size)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {

        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val binding =
                ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BusinessViewHolder(binding)
          //  val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
         //   ItemViewHolder(view)
        } else {
            val binding =
                ProgressLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.progressbar.indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                binding.progressbar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            }
            LoadingViewHolder(binding)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (businessList[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType ==  Constant.VIEW_TYPE_ITEM){
            val currentItem = businessList.get(position)
            val businessHolder: BusinessViewHolder = holder as BusinessViewHolder
            currentItem?.let { businessHolder.bind(it) }
        }

    }
    inner class BusinessViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = businessList.get(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(business: Business) {
            binding.apply {
                expandItem(this, business == expandedModel, animate = false)
                scaleDownItem(this, position, isScaledDown)

                chevron.setOnClickListener {
                    if (expandedModel == null) {

                        // expand clicked view
                        expandItem(this, expand = true, animate = true)
                        expandedModel = business
                    } else if (expandedModel == business) {

                        // collapse clicked view
                        expandItem(this, expand = false, animate = true)
                        expandedModel = null
                    } else {

                        // collapse previously expanded view
                        val expandedModelPosition = businessList.indexOf(expandedModel!!)
                        val oldViewHolder =
                            recyclerView.findViewHolderForAdapterPosition(expandedModelPosition) as? ItemListBinding
                        if (oldViewHolder != null) expandItem(oldViewHolder, expand = false, animate = true)

                        // expand clicked view
                        expandItem(this, expand = true, animate = true)
                        expandedModel = business
                    }
                }
                Glide.with(itemView)
                    .load(business.image_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)


                tvName.text = "Name: ${business.name}"
                tvPrice.text = "Price: {${business.price}}"
                tvRating.text = "Rating: {${business.rating}}"
                tvCategory.text="Category:"
                for (category in business.category) {
                    tvCategory.append(" ${category.title} ")

                }
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

        val itemExpanded = position >= 0 && businessList[position] == expandedModel
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

    interface OnItemClickListener {
        fun onItemClick(business: Business)
    }

}

