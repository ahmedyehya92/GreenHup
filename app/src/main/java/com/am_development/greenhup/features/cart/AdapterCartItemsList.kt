package com.am_development.greenhup.features.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.am_development.entities.CartItem
import com.am_development.entities.ITEM
import com.am_development.entities.LOADING
import com.am_development.greenhup.R
import com.am_development.greenhup.core.PaginationAdapterCallBack
import com.am_development.greenhup.customviews.CustomeFontTextView
import com.am_development.greenhup.features.categories.AdapterPlants
import com.bumptech.glide.Glide
import com.wang.avi.AVLoadingIndicatorView

class AdapterCartItemsList (
    private val mContext: Context,
    cartList: MutableList<CartItem>?
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var cartList: MutableList<CartItem>? = null
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var mCallback: PaginationAdapterCallBack? = null
    private var errorMsg: String? = null


    override fun getItemCount() =  cartList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == cartList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(r: CartItem) {
        cartList!!.add(r)
        notifyItemInserted(cartList!!.size - 1)
    }

    fun addAll(opResults: MutableList<CartItem>) {
        for (result in opResults) {
            add(result)
        }
    }

    fun getItem(position: Int): CartItem {
        return cartList!![position]
    }

    val isEmpty: Boolean
        get() = itemCount == 0


    fun addLoadingFooter() {
        isLoadingAdded = true
        //add(new OpportunityModel());
        add(getItem(cartList!!.size - 1))
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {

            val position = cartList!!.size - 1
            val result: CartItem = getItem(position)
            if (result != null) {
                cartList!!.removeAt(position)
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
                    R.layout.item_cart_list, viewGroup, false
                )
                viewHolder = CartViewHolder(viewItem)
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
        val model: CartItem = cartList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val cartViewHolder =
                    holder as CartViewHolder

                Glide.with(mContext)
                    .load(model.imageUrl)
                    .into(cartViewHolder.im_plant)


                cartViewHolder.tv_name?.text = model.name
                model.totalPrice = model.unitPrice*model.quantity
                cartViewHolder.tv_price?.text = model.totalPrice.toString()
                cartViewHolder.btn_add!!.setOnClickListener {
                    if(model.quantity+1 <= model.maxQuantity) {
                        model.quantity += 1
                        model.totalPrice = model.unitPrice * model.quantity
                        notifyItemChanged(position)
                    }
                }

                cartViewHolder.btn_remove!!.setOnClickListener {
                    if(model.quantity-1 >= 0) {
                        model.quantity -= 1
                        model.totalPrice = model.unitPrice * model.quantity
                        notifyItemChanged(position)
                    }
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
        notifyItemChanged(cartList!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }


    inner class CartViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

        val im_plant by lazy { itemView.findViewById<ImageView>(R.id.im_plant) }
        val tv_name by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_name) }
        val tv_price by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_total_price) }
        val btn_add by lazy { itemView.findViewById<ImageView>(R.id.btn_add) }
        val btn_remove by lazy { itemView.findViewById<ImageView>(R.id.btn_remove) }
    }

    inner class LoadingVH(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val mProgressBar by lazy { itemView.findViewById<AVLoadingIndicatorView>(R.id.avi_loading_more) }
        val mRetryBtn by lazy { itemView.findViewById<LinearLayout>(R.id.btn_try_again) }
        val footerLayout by lazy { itemView.findViewById<LinearLayout>(R.id.loadmore_errorlayout) }
    }
    init {
        this.cartList = cartList
    }
    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }
}