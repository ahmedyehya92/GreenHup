package com.openet.greenhup.features.package_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.openet.entities.KEY_PACKAGE
import com.openet.entities.MPackage
import com.openet.entities.Plant
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_package_details.*

class PackageDetailsActivity : BaseActivity(), PackageDetailsView {
    var mPackage: MPackage?= null
    var addedToCart= false
    private val presenter: PackageDetailsPresenter by lazy {
        PackageDetailsImplPresenter(this, this)
    }


    private val plantsList: MutableList<Plant> = ArrayList()
    private var adapterPackageProductsList: AdapterPackageProductsList? = null

    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> mPackage?.let {mPackage-> presenter.getPackageDetails(mPackage.id) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_details)

        setInitiateValues()


        setupRequestHandlerView()

        adapterPackageProductsList= AdapterPackageProductsList(this, plantsList)
        rv_plants.adapter= adapterPackageProductsList
      /*  val planList: MutableList<Plant> = ArrayList()
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        addToPlants(planList)*/

        mPackage?.let { presenter.getPackageDetails(it.id) }


    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }

    override fun addPackageDetails(packageDetails: MPackage) {
        adapterPackageProductsList?.addAll(packageDetails.plantsList)
        addHtmlDescriptionToWebView(packageDetails.packageDetails)
        Glide.with(this)
            .load(packageDetails.imageUrl)
            .into(im_package)

        tv_title.text= packageDetails.name
        tv_price.text= "${getString(R.string.currency)} ${packageDetails.price}"
        tv_seller_name.text= packageDetails.sellerName

        addedToCart= packageDetails.addedToCart

        changeAddedToCartStateButton(addedToCart)

        btn_add_to_cart.setOnClickListener {
            presenter.changeCartStae(packageDetails.id, addedToCart, "1")
        }

    }

    override fun changeAddToCartState(added: Boolean) {
        addedToCart= added
        changeAddedToCartStateButton(addedToCart)
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
    }

    companion object {
        fun instantiateIntent(context: Context, mPackage: MPackage): Intent {
            return Intent(context, PackageDetailsActivity::class.java).apply { this.putExtra(
                KEY_PACKAGE, mPackage
            ) }
        }
    }

    private fun setInitiateValues() {
        mPackage= intent.getSerializableExtra(KEY_PACKAGE) as MPackage
    }

    private fun addHtmlDescriptionToWebView(details: String?) {
        val webSettings: WebSettings = web_view_package_details.settings
        webSettings.javaScriptEnabled = true
        webSettings.lightTouchEnabled = true;
        webSettings.javaScriptEnabled = true;
        webSettings.setGeolocationEnabled(true);

        web_view_package_details.loadData(details, "text/html", "UTF-8");
    }

    fun changeAddedToCartStateButton(added: Boolean)
    {
        if(added)
        {
            btn_add_to_cart.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            tv_buy_button_title.text= getString(R.string.remove_from_cart)
            im_icon_delete.visibility= View.VISIBLE
        }
        else
        {
            btn_add_to_cart.setBackgroundColor(ContextCompat.getColor(this, R.color.mossy_green))
            tv_buy_button_title.text= getString(R.string.add_to_cart)
            im_icon_delete.visibility= View.GONE
        }
    }

}
