package com.openet.greenhup.features.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.openet.entities.CartItem

import com.openet.greenhup.R
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.features.save_order.SaveOrderActivity
import com.openet.greenhup.features.sign.SignActivity
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.lout_loading_interval_view_container

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), CartView, AdapterCartItemsList.customButtonListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var cartList: MutableList<CartItem> = ArrayList()

    private var tax= 0
    private var adapterCartItemsList: AdapterCartItemsList? = null

    private val REQUEST_CODE_LOGIN = 1

    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getCart()
            2 -> {
                Intent(requireContext(), SignActivity::class.java).apply {

                    startActivityForResult(
                        this,
                        REQUEST_CODE_LOGIN
                    )
                }
            }
        }
    }

    private val presenter: CartPresenter by lazy {
        CartImplPresenter(this, requireContext())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterCartItemsList= AdapterCartItemsList(context!!, cartList)
        adapterCartItemsList?.setCustomButtonListner(this)
        rv_cart.adapter= adapterCartItemsList

        setupRequestHandlerView()
        //presenter.getCart()

        

        btn_checkout.setOnClickListener {
            startActivity(Intent(requireActivity(), SaveOrderActivity::class.java))
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, requireContext(), false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun addToCartList(items: MutableList<CartItem>) {
        cartList= items
        Log.e(CartFragment::class.java.simpleName, "size= ${items.size}")
        adapterCartItemsList?.addAll(cartList)
        tv_tax.text= "${getString(R.string.currency)} ${tax}"



        updateTotalPrice()


    }

    override fun changeItemAmount(position: Int, newQuantity: Int) {
        cartList[position].quantity=newQuantity
        //cartList[position].totalPrice = cartList[position].unitPrice*cartList[position].quantity
        adapterCartItemsList?.notifyItemChanged(position)
        Log.e(CartFragment::class.java.simpleName, "total price= ${cartList[position].totalPrice}")
        updateTotalPrice()
    }

    override fun removeItemFromCart(position: Int) {
        //cartList.removeAt(position)
        adapterCartItemsList?.remove(cartList[position])
        adapterCartItemsList?.notifyItemChanged(position)
        updateTotalPrice()
    }

    override fun showLoading() {
        requestIntervalHandler.showLoadingView(null)
    }

    override fun finishLoading() {
        requestIntervalHandler.finishLoading()
    }

    override fun connectionError(message: String?) {
        requestIntervalHandler.showErrorView(getString(R.string.error_connection))
    }

    override fun faildLoading(message: Any) {
        requestIntervalHandler.showNotLoggedInView()
    }

    override fun onItemAddClickListener(itemId: String, position: Int, newQuantity: Int, type: String) {
            presenter.changeCartItemAmount(itemId, position, newQuantity, type)

    }

    override fun onItemRemoveClickListener(itemId: String, position: Int, newQuantity: Int, type: String) {
        if(newQuantity!=0)
            presenter.changeCartItemAmount(itemId, position, newQuantity, type)
    }

    override fun onItemDeleteClickListener(itemId: String, position: Int, type: String) {
        val isProduct= type=="product"
        presenter.removeItemFromCart(itemId,position, isProduct)
    }

    override fun onResume() {
        super.onResume()
        Log.e("CartFragment", "onResume")
        adapterCartItemsList?.clear()
        presenter.getCart()
       //
     //   presenter.getCart()
    }

    fun updateTotalPrice()
    {

        cartList.forEach {
            it.totalPrice = it.unitPrice*it.quantity
        }

        var subTotal = 0f
        adapterCartItemsList?.getList()?.forEach {
            subTotal += it.totalPrice
        }

        tv_suptotal.text = "${getString(R.string.currency)} ${subTotal}"
        tv_total.text= "${getString(R.string.currency)} ${(subTotal+ tax)}"

        adapterCartItemsList?.let {
            btn_checkout.isEnabled = it.itemCount != 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_OK)
        {
            presenter.getCart()
        }

    }




}
