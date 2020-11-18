package com.openet.greenhup.features.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.openet.entities.CartItem
import com.openet.entities.ITEM
import com.openet.entities.LOADING
import com.openet.greenhup.R
import com.openet.greenhup.core.PaginationAdapterCallBack
import com.openet.greenhup.customviews.CustomeFontTextView
import com.openet.greenhup.features.categories_vendor.AdapterPlants
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
    private var customListener: customButtonListener? = null

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


    fun remove(r: CartItem) {
        cartList?.let {
            val position = it.indexOf(r)
            if (position > -1) {
                it.removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
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
                cartViewHolder.tv_quantity.text = model.quantity.toString()
                cartViewHolder.tv_price?.text = "${mContext.getString(R.string.currency)} ${model.totalPrice}"
                cartViewHolder.btn_add!!.setOnClickListener {
                  /*  if(model.quantity+1 <= model.maxQuantity) {
                        model.quantity += 1
                        model.totalPrice = model.unitPrice * model.quantity
                        notifyItemChanged(position)
                    }*/
                    customListener?.onItemAddClickListener(model.id, position, model.quantity+1, model.type)
                }

                cartViewHolder.btn_remove!!.setOnClickListener {
                    /*if(model.quantity-1 >= 0) {
                        model.quantity -= 1
                        model.totalPrice = model.unitPrice * model.quantity
                        notifyItemChanged(position)
                    }*/


                    customListener?.onItemRemoveClickListener(model.id, position, model.quantity-1, model.type)
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

    fun getList():MutableList<CartItem>?
    {
        return cartList
    }


    inner class CartViewHolder(itemView: View) :
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
        this.cartList = cartList
    }
    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }

    interface customButtonListener {
        fun onItemAddClickListener(itemId: String, position: Int, newQuantity: Int, type: String)
        fun onItemRemoveClickListener(itemId: String, position: Int, newQuantity: Int, type: String)
    }

    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }
}