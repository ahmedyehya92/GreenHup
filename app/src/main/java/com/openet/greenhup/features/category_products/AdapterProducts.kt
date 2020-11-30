package com.openet.greenhup.features.category_products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.openet.entities.Plant
import com.openet.greenhup.R
import com.openet.greenhup.core.PaginationAdapterCallBack
import com.openet.greenhup.customviews.CustomeFontTextView
import com.bumptech.glide.Glide
import com.wang.avi.AVLoadingIndicatorView

class AdapterProducts (
    private val context: Context,
    arrayList: MutableList<Plant>?
): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder?>() {

    private val arrayList: MutableList<Plant>?
    private var customListener: customButtonListener? = null
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var requestIsFinished= false
    private var mCallback: PaginationAdapterCallBack? = null
    private var errorMsg: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null
        val mInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View = mInflater.inflate(
                    R.layout.item_plants_list_2, parent, false
                )
                viewHolder = PlantViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    mInflater.inflate(R.layout.view_loading_footer, parent, false)
                viewHolder = LoadingVH(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model: Plant = arrayList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val plantViewHolder =
                    holder as PlantViewHolder

                Glide.with(context)
                    .load(model.imageUrl)
                    .into(plantViewHolder.im_plant)


                plantViewHolder.tv_position.text= model.categoryName
                plantViewHolder.tv_name?.text = model.name
                plantViewHolder.tv_price?.text = "${context.getString(R.string.currency)} ${model.price.toFloat()}"
                plantViewHolder.lout_container!!.setOnClickListener {
                    customListener!!.onItemPlantsClickListener(
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

    override fun getItemCount(): Int =  arrayList!!.size

    interface customButtonListener {
        fun onItemPlantsClickListener(plant: Plant)
    }

    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }

    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }

    fun showRetry(show: Boolean, errorMsg: String?) {
        retryPageLoad = show
        notifyItemChanged(arrayList!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == arrayList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(r: Plant) {
        arrayList!!.add(r)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(opResults: MutableList<Plant>) {
        for (result in opResults) {
            add(result)
        }
    }

    fun remove(r: Plant?) {
        val position = arrayList!!.indexOf(r)
        if (position > -1) {
            arrayList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun getItem(position: Int): Plant {
        return arrayList!![position]
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    fun addLoadingFooter() {
        isLoadingAdded = true
        //add(new OpportunityModel());
        add(getItem(arrayList!!.size - 1))
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {

            val position = arrayList!!.size - 1
            val result: Plant = getItem(position)
            if (result != null) {
                arrayList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        isLoadingAdded = false
    }

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    init {
        this.arrayList = arrayList
    }

    inner class PlantViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val im_plant by lazy { itemView.findViewById<ImageView>(R.id.im_plant) }
        val tv_name by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_name) }
        val tv_position by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_position) }
        val lout_container by lazy { itemView.findViewById<CardView>(R.id.lout_main) }
        val tv_price by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_price) }
    }

    protected inner class LoadingVH(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val mProgressBar by lazy { itemView.findViewById<AVLoadingIndicatorView>(R.id.avi_loading_more) }
        val mRetryBtn by lazy { itemView.findViewById<LinearLayout>(R.id.btn_try_again) }
        val footerLayout by lazy { itemView.findViewById<LinearLayout>(R.id.loadmore_errorlayout) }


    }

}