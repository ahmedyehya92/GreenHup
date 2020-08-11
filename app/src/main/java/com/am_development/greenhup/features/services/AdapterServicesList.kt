package com.am_development.greenhup.features.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.am_development.entities.ITEM
import com.am_development.entities.LOADING
import com.am_development.entities.ServiceItem
import com.am_development.greenhup.R
import com.am_development.greenhup.core.PaginationAdapterCallBack
import com.am_development.greenhup.customviews.CustomeFontTextView
import com.am_development.greenhup.features.categories_vendor.AdapterPlants
import com.bumptech.glide.Glide
import com.wang.avi.AVLoadingIndicatorView

class AdapterServicesList (
    private val mContext: Context,
    servicesList: MutableList<ServiceItem>?
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var servicesList: MutableList<ServiceItem>? = null
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var mCallback: PaginationAdapterCallBack? = null
    private var customListener: customButtonListener? = null
    private var errorMsg: String? = null


    override fun getItemCount() =  servicesList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == servicesList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(r: ServiceItem) {
        servicesList!!.add(r)
        notifyItemInserted(servicesList!!.size - 1)
    }

    fun addAll(opResults: MutableList<ServiceItem>) {
        for (result in opResults) {
            add(result)
        }
    }

    fun getItem(position: Int): ServiceItem {
        return servicesList!![position]
    }

    val isEmpty: Boolean
        get() = itemCount == 0


    fun addLoadingFooter() {
        isLoadingAdded = true
        //add(new OpportunityModel());
        add(getItem(servicesList!!.size - 1))
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {

            val position = servicesList!!.size - 1
            val result: ServiceItem = getItem(position)
            if (result != null) {
                servicesList!!.removeAt(position)
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
                    R.layout.item_services_list, viewGroup, false
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
        val model: ServiceItem = servicesList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val plantViewHolder =
                    holder as PlantViewHolder

                Glide.with(mContext)
                    .load(model.imageUrl)
                    .into(plantViewHolder.im_plant)


                plantViewHolder.tv_name?.text = model.name
                plantViewHolder.tv_price?.text = model.price
                plantViewHolder.btn_get_now!!.setOnClickListener {
                    customListener!!.onItemServiceClickListener(
                        model
                    )
                }
            }
            LOADING -> {
                val loadingVH = holder as AdapterPlants.LoadingVH
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
        notifyItemChanged(servicesList!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }


    inner class PlantViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

        val im_plant by lazy { itemView.findViewById<ImageView>(R.id.im_plant) }
        val tv_name by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_name) }
        val tv_price by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_price) }
        val btn_get_now by lazy { itemView.findViewById<CustomeFontTextView>(R.id.btn_get_now)}
    }

    inner class LoadingVH(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val mProgressBar by lazy { itemView.findViewById<AVLoadingIndicatorView>(R.id.avi_loading_more) }
        val mRetryBtn by lazy { itemView.findViewById<LinearLayout>(R.id.btn_try_again) }
        val footerLayout by lazy { itemView.findViewById<LinearLayout>(R.id.loadmore_errorlayout) }
    }
    init {
        this.servicesList = servicesList
    }

    interface customButtonListener {
        fun onItemServiceClickListener(plant: ServiceItem)
    }

    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }

    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }
}