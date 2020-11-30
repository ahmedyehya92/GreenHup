package com.openet.greenhup.features.my_orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openet.entities.CartItem
import com.openet.entities.ITEM
import com.openet.entities.LOADING
import com.openet.entities.Order
import com.openet.greenhup.R
import com.openet.greenhup.core.PaginationAdapterCallBack
import com.openet.greenhup.customviews.CustomeFontTextView
import com.openet.greenhup.features.cart.AdapterCartItemsList
import com.openet.greenhup.features.categories_vendor.AdapterPlants
import com.wang.avi.AVLoadingIndicatorView

class AdapterMyOrdersList (
    private val mContext: Context,
    ordersList: MutableList<Order>?
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var ordersList: MutableList<Order>? = null
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var mCallback: PaginationAdapterCallBack? = null
    private var errorMsg: String? = null
    private var customListener: customButtonListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null
        val mInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View = mInflater.inflate(
                    R.layout.item_view_orders_list, parent, false
                )
                viewHolder = OrderViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    mInflater.inflate(R.layout.view_loading_footer, parent, false)
                viewHolder = LoadingVH(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val model: Order = ordersList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val cartViewHolder =
                    holder as OrderViewHolder

                cartViewHolder.tv_date.text= model.orderdate
                cartViewHolder.tv_ref_code.text= model.refcode
                if(model.paid.isEmpty())
                {
                    cartViewHolder.tv_is_paid.text = mContext.getString(R.string.no)
                    cartViewHolder.btn_pay_online.visibility= View.VISIBLE
                } else {
                    cartViewHolder.tv_is_paid.text = mContext.getString(R.string.yes)
                    cartViewHolder.btn_pay_online.visibility= View.GONE
                }
                if(model.paymentmethod == "1")
                    cartViewHolder.tv_payment_method.text= context.getString(R.string.payment_online)

                else
                    cartViewHolder.tv_payment_method.text= mContext.getString(R.string.cash_on_delivery)

                cartViewHolder.tv_total_price.text= "${mContext.getString(R.string.currency)} ${model.grandtotal}"


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
        notifyItemChanged(ordersList!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }

    override fun getItemCount() =  ordersList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == ordersList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(r: Order) {
        ordersList!!.add(r)
        notifyItemInserted(ordersList!!.size - 1)
    }

    fun addAll(opResults: MutableList<Order>) {
        for (result in opResults) {
            add(result)
        }
    }

    fun getItem(position: Int): Order {
        return ordersList!![position]
    }


    fun remove(r: Order) {
        ordersList?.let {
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
        add(getItem(ordersList!!.size - 1))
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {

            val position = ordersList!!.size - 1
            val result: Order = getItem(position)
            if (result != null) {
                ordersList!!.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        isLoadingAdded = false
    }

    inner class OrderViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val tv_date by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_date) }
        val tv_ref_code by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_ref_code) }
        val tv_is_paid by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_is_paid) }
        val btn_pay_online by lazy { itemView.findViewById<CustomeFontTextView>(R.id.btn_pay_online) }
        val tv_payment_method by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_payment_method) }
        val tv_total_price by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_total_price) }
        val tv_status by lazy { itemView.findViewById<CustomeFontTextView>(R.id.tv_status) }

    }

    inner class LoadingVH(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        val mProgressBar by lazy { itemView.findViewById<AVLoadingIndicatorView>(R.id.avi_loading_more) }
        val mRetryBtn by lazy { itemView.findViewById<LinearLayout>(R.id.btn_try_again) }
        val footerLayout by lazy { itemView.findViewById<LinearLayout>(R.id.loadmore_errorlayout) }

    }

    init {
        this.ordersList = ordersList
    }
    fun setPagingAdapterCallback(pagingAdapterCallback: PaginationAdapterCallBack?) {
        mCallback = pagingAdapterCallback
    }

    interface customButtonListener {
        fun onClickPayOnline(orderId: Int, position: Int)
    }
    fun setCustomButtonListner(listener: customButtonListener?) {
        customListener = listener
    }

}