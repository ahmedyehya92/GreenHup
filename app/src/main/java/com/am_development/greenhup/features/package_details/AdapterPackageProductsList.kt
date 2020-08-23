package com.am_development.greenhup.features.package_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.am_development.entities.ITEM
import com.am_development.entities.LOADING
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.core.PaginationAdapterCallBack
import com.am_development.greenhup.customviews.CustomeFontTextView
import com.bumptech.glide.Glide
import com.wang.avi.AVLoadingIndicatorView

class AdapterPackageProductsList (
    private val mContext: Context,
    plantsList: MutableList<Plant>?
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var plantsList: MutableList<Plant>? = null
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
                    R.layout.item_pakage_product_list_view, viewGroup, false
                )
                viewHolder = PlantsViewHolder(viewItem)
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
                val plantsViewHolder =
                    holder as PlantsViewHolder

                Glide.with(mContext)
                    .load(model.imageUrl)
                    .into(plantsViewHolder.im_plant)


                plantsViewHolder.tv_name?.text = model.name
                plantsViewHolder.tv_price.text = model.price
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


    inner class PlantsViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

        val im_plant by lazy { itemView.findViewById<ImageView>(R.id.im_plant) }
        val tv_name by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_name) }
        val tv_price by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_total_price) }
        val btn_add by lazy { itemView.findViewById<ImageView>(R.id.btn_add) }
        val btn_remove by lazy { itemView.findViewById<ImageView>(R.id.btn_remove) }
        val tv_quantity  by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_quantity) }
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
    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }
}