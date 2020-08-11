package com.am_development.greenhup.features.categories_vendor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.am_development.entities.Category
import com.am_development.entities.ITEM
import com.am_development.entities.LOADING
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.core.PaginationAdapterCallBack
import com.am_development.greenhup.customviews.CustomeFontTextView
import com.wang.avi.AVLoadingIndicatorView

class AdapterCategoriesList(
    private val mContext: Context,
    categoriesList: MutableList<Category>?
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder?>(), AdapterPlants.customButtonListener
{
    private var categoriesList: MutableList<Category>? = null
    private var customListener: customButtonListener? = null
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var mCallback: PaginationAdapterCallBack? = null
    private var errorMsg: String? = null

    override fun getItemCount() =  categoriesList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == categoriesList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(r: Category) {
        categoriesList!!.add(r)
        notifyItemInserted(categoriesList!!.size - 1)
    }

    fun addAll(opResults: MutableList<Category>) {
        for (result in opResults) {
            add(result)
        }
    }

    fun getItem(position: Int): Category {
        return categoriesList!![position]
    }

    val isEmpty: Boolean
        get() = itemCount == 0


    fun addLoadingFooter() {
        isLoadingAdded = true
        //add(new OpportunityModel());
        add(getItem(categoriesList!!.size - 1))
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {

            val position = categoriesList!!.size - 1
            val result: Category = getItem(position)
            if (result != null) {
                categoriesList!!.removeAt(position)
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
                    R.layout.item_category_vendor_list, viewGroup, false
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
        val model: Category = categoriesList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val plantViewHolder =
                    holder as PlantViewHolder


                val adapterPlants = AdapterPlants(mContext, model.plantsList)
                adapterPlants.setCustomButtonListner(this)
                plantViewHolder.rv_plants.adapter = adapterPlants

                plantViewHolder.tv_name?.text = model.name
                plantViewHolder.btn_more!!.setOnClickListener {
                    customListener!!.onItemMoreClickListner(
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
        notifyItemChanged(categoriesList!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }

    inner class PlantViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView!!) {
        val tv_name by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_name) }
        val rv_plants by lazy { itemView.findViewById<RecyclerView>(R.id.rv_plants) }
        val btn_more by lazy { itemView.findViewById<CustomeFontTextView>(R.id.btn_more) }

    }

    inner class LoadingVH(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val mProgressBar by lazy { itemView.findViewById<AVLoadingIndicatorView>(R.id.avi_loading_more) }
        val mRetryBtn by lazy { itemView.findViewById<LinearLayout>(R.id.btn_try_again) }
        val footerLayout by lazy { itemView.findViewById<LinearLayout>(R.id.loadmore_errorlayout) }
    }

    interface customButtonListener {
        fun onItemMoreClickListner(productModel: Category)
        fun omItemPlantClickListener(plant: Plant)
    }

    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }

    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }

    init {
        this.categoriesList = categoriesList
    }

    override fun onItemPlantsClickListener(plant: Plant) {
        customListener?.omItemPlantClickListener(plant)
    }

}