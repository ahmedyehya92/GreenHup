package com.am_development.greenhup.features.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.am_development.entities.ITEM
import com.am_development.entities.LOADING
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.core.PaginationAdapterCallBack
import com.am_development.greenhup.customviews.CustomeFontTextView
import com.bumptech.glide.Glide
import com.wang.avi.AVLoadingIndicatorView

class AdapterHorizontalPlantsList (
    private val mContext: Context,
    plantsList: MutableList<Plant>?
): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder?>()
{
    private var plantsList: MutableList<Plant>? = null
    private var customListener: customButtonListener? = null
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var mCallback: PaginationAdapterCallBack? = null
    private var errorMsg: String? = null


    override fun getItemCount() =  plantsList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == plantsList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(r: Plant) {
        plantsList!!.add(r)
        notifyItemInserted(plantsList!!.size - 1)
    }

    fun addAll(opResults: MutableList<Plant>) {
        for (result in opResults) {
            add(result)
        }
    }

    fun getItem(position: Int): Plant {
        return plantsList!![position]
    }

    val isEmpty: Boolean
        get() = itemCount == 0


    fun addLoadingFooter() {
        isLoadingAdded = true
        //add(new OpportunityModel());
        add(getItem(plantsList!!.size - 1))
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {

            val position = plantsList!!.size - 1
            val result: Plant = getItem(position)
            if (result != null) {
                plantsList!!.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        isLoadingAdded = false
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null
        val mInflater = LayoutInflater.from(viewGroup.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View = mInflater.inflate(
                    R.layout.item_home_horizontal_list, viewGroup, false
                )
                viewHolder = PlantViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    mInflater.inflate(R.layout.view_loading_footer, viewGroup, false)
                viewHolder = LoadingVH(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val model: Plant = plantsList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val plantViewHolder =
                    holder as PlantViewHolder

                Glide.with(mContext)
                    .load(model.imageUrl)
                    .into(plantViewHolder.im_plant)

                plantViewHolder.tv_name?.text = model.name
                plantViewHolder.tv_price?.text = model.price
                plantViewHolder.lout_container!!.setOnClickListener {
                    customListener!!.onItemHorizontalListClickListener(
                        model
                    )
                }
            }
            LOADING -> {
                val loadingVH = holder as LoadingVH
                val layoutParams =
                    loadingVH.itemView.layoutParams as androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams
                layoutParams.isFullSpan = true
                if (retryPageLoad) {
                    holder.footerLayout.visibility = View.VISIBLE
                    holder.mProgressBar.visibility = View.GONE

                } else run {
                    holder.footerLayout.visibility = View.GONE
                    holder.mProgressBar.visibility = View.VISIBLE
                }

                holder.mRetryBtn.setOnClickListener {
                    showRetry(false,"Error!")
                    mCallback!!.retryPageLoad()
                }
            }
        }
    }

    fun showRetry(show: Boolean, errorMsg: String?) {
        retryPageLoad = show
        notifyItemChanged(plantsList!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }

    inner class PlantViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

        val im_plant by lazy { itemView.findViewById<ImageView>(R.id.im_plant) }
        val tv_name by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_name) }
        val lout_container by lazy { itemView.findViewById<ConstraintLayout>(R.id.lout_main) }
        val tv_price by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_price) }

    }

    inner class LoadingVH(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val mProgressBar by lazy { itemView.findViewById<AVLoadingIndicatorView>(R.id.avi_loading_more) }
        val mRetryBtn by lazy { itemView.findViewById<LinearLayout>(R.id.btn_try_again) }
        val footerLayout by lazy { itemView.findViewById<LinearLayout>(R.id.loadmore_errorlayout) }
    }
    init {
        this.plantsList = plantsList
    }

    interface customButtonListener {
        fun onItemHorizontalListClickListener(plant: Plant)
    }

    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }

    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }

}