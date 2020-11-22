package com.openet.greenhup.features.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.openet.entities.KEY_PLANT
import com.openet.entities.Plant
import com.openet.entities.SliderItem
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.customviews.CustomeFontTextView
import com.openet.greenhup.features.sign.SignActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream


class PlantDetailsActivity : BaseActivity(), PlantDetailsView {
    var plant: Plant?= null
    var tvAmount: CustomeFontTextView?= null
    private var favorited = false
    private val REQUEST_CODE_LOGIN = 105
    private val presenter: PlantDetailsPresenter by lazy {
        PlantDetailsImplPresenter(this, this)
    }

    private lateinit var requestIntervalHandler: RequestIntervalHandler2

    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> plant?.let { plant ->
                presenter.getDetails(plant.id)
            }

            2 -> {
                Intent(this, SignActivity::class.java).apply {

                    startActivityForResult(
                        this,
                        REQUEST_CODE_LOGIN
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setInitiateValues()

        setupRequestHandlerView()

        plant?.let {
            presenter.getDetails(it.id)
        }

        //plant?.let { addDetails(it) }


    }

    private fun setupRequestHandlerView() {
        requestIntervalHandler =
            RequestIntervalHandler2(lout_loading_interval_view_container, this, false)
        requestIntervalHandler.tryAgainTrigger.observe(this, tryAgainTriggerObserever)
        requestIntervalHandler.setMessageErrorTextColor(R.color.black)
    }


    override fun addDetails(plant: Plant) {

        tv_name.text= plant.name

        favorite_select.isSelected= plant.isliked
        favorited= plant.isliked

        btn_favorite.setOnClickListener {
            presenter.changeFavoriteStateProduct(plant.id, favorited)
        }

        tv_category_name.text= plant.categoryName
        tv_code.text= plant.code
        //rating_bar.rating= plant.

        setSlider(plant.gallery)

        Glide.with(this)
            .load(plant.imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    im_plant.setOnClickListener {
                        im_plant.invalidate()
                        val drawable: BitmapDrawable = im_plant.getDrawable() as BitmapDrawable
                        val bitmap: Bitmap = drawable.getBitmap()


                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray: ByteArray = stream.toByteArray()

                        val b = Bundle()
                        b.putByteArray("image", byteArray)
                        DialogFullScreenImage.newInstance(byteArray).show(supportFragmentManager,"DialogFullScreenImage")
                    }

                    return false
                }

            })
            .into(im_plant)
        tv_price.text= "${getString(R.string.start_from)} ${getString(R.string.currency)} ${plant.price}"
        tv_vendor_name.text= plant.vendorName

        addHtmlDescriptionToWebView(plant.details)

        if(plant.specsList.size != 0) {
            val titlesRow= TableRow(this)
            var valueRow: TableRow?= TableRow(this)
            var titelsTextView: TextView?= null
            var attributeTextView: TextView?= null
            var priceTextView: TextView?= null
            plant.specsList.forEachIndexed { index, specification ->
                titelsTextView= TextView(this)
                TextViewCompat.setTextAppearance(titelsTextView!!, R.style.TitleTextAppearence)
                titelsTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
               /* if(index < plant.specsCats?.size!!-1) {

                }*/
                titelsTextView?.text= "${specification.specName} | "


                titlesRow.addView(titelsTextView)



            }

            titelsTextView= TextView(this)
            TextViewCompat.setTextAppearance(titelsTextView!!, R.style.TitleTextAppearence)
            titelsTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
            titelsTextView?.text= "Price"
            titlesRow.addView(titelsTextView)

            table_specification.addView(titlesRow)

            plant.specsCats?.forEachIndexed { i, specsCatItem ->
                Log.e(this::class.java.simpleName, "specscatPosition= $i")
                plant.specsList.forEachIndexed { t, specefication->
                    //if(specsCatItem.specifications.filter { it.specId== specefication.specId} != null)
                    if((specsCatItem.specifications.filter { it.specId == specefication.specId }).isNotEmpty()) {
                        Log.e(this::class.java.simpleName, "specificationPosition= $t")
                        attributeTextView = TextView(this)
                        attributeTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
                        attributeTextView?.text =
                            "${specsCatItem.specifications.filter { it.specId == specefication.specId }[0].value}  "

                        valueRow?.addView(attributeTextView)
                    }

                }



                /*  it.specifications.forEach {
                    if(specification.specId == it.specId) {
                        attributeTextView = TextView(this)
                        attributeTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
                        attributeTextView?.text= it.value
                    }
                    else
                    {
                        attributeTextView = TextView(this)
                        attributeTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
                        attributeTextView?.text= ""
                    }*/



                priceTextView= TextView(this)
                priceTextView?.setTextColor(ContextCompat.getColor(this, R.color.shamrock_green))
                priceTextView?.text= "${getString(R.string.currency)} ${specsCatItem.price.toFloat()}"
                valueRow?.addView(priceTextView)

                val mInflater = layoutInflater.inflate(
                    R.layout.view_amount_changer,
                    tabLayout,
                    false
                )

                val tv_amount= mInflater.findViewById<CustomeFontTextView>(R.id.tv_amount)
                val btn_add= mInflater.findViewById<ImageView>(R.id.btn_add)
                val btn_remove= mInflater.findViewById<ImageView>(R.id.btn_remove)



                tv_amount.text= specsCatItem.amountInCart.toString()

                btn_add.setOnClickListener {
                    this.tvAmount= tv_amount
                    presenter.changeCartItemAmount(
                        specsCatItem.id,
                        tv_amount.text.toString().toInt() + 1
                    )
                    //tv_amount.text="3"
                }

                btn_remove.setOnClickListener {
                    if(tv_amount.text.toString().toInt() != 0) {
                        this.tvAmount = tv_amount
                        presenter.changeCartItemAmount(
                            specsCatItem.id,
                            tv_amount.text.toString().toInt() - 1
                        )
                    }
                    //tv_amount.text="2"
                }

                valueRow?.addView(mInflater)




                table_specification.addView(valueRow)
                valueRow= TableRow(this)
                }


            }


        }

    override fun changeItemAmount(newQuantity: Int) {
        Log.e("PlantDetailsAct", "AddedToCart")
        tvAmount?.text= newQuantity.toString()
    }

    override fun changeFavoriteState(favoriteState: Boolean) {
        favorited = favoriteState
        favorite_select.isSelected= favoriteState
    }

    private fun setSlider(gallery: MutableList<SliderItem>?) {
        gallery?.let{

            if(gallery.size==0)
            {
                lout_arrows.visibility= View.GONE
                page_slider.visibility= View.GONE
            }

            else
            {
                lout_arrows.visibility= View.VISIBLE
                page_slider.visibility= View.VISIBLE

                val sliderGalleryAdapter = AdapterSliderGallery(it, this)
                page_slider.adapter = sliderGalleryAdapter


                lout_left_arrow_package.setOnClickListener {
                    if(page_slider.currentItem >0)
                        page_slider.currentItem-=1
                }
                lout_right_arrow_package.setOnClickListener {
                    if(page_slider.currentItem <gallery.size-1)
                        page_slider.currentItem+=1
                }
            }



        }


    }

    //tv_vendor_name.text= plant.
        /*if(plant.specifications?.size != 0)
        {
            lout_specifications.visibility= View.VISIBLE
            val specifications=""
            plant.specifications?.forEach {
                specifications.plus(it.specName)
                specifications.plus(":")
                specifications.plus(it.value)
                specifications.plus("\n")
            }
            tv_specifications.text= specifications
        }*/




    private fun addHtmlDescriptionToWebView(details: String?) {

        details?.let { details ->
            if(details.isEmpty()) {
                tv_details_label.visibility = View.GONE
                web_view_details.visibility= View.GONE
            }

            else
            {
                tv_details_label.visibility = View.VISIBLE
                web_view_details.visibility= View.VISIBLE

                val webSettings: WebSettings = web_view_details.settings
                webSettings.javaScriptEnabled = true
                webSettings.lightTouchEnabled = true;
                webSettings.javaScriptEnabled = true;
                webSettings.setGeolocationEnabled(true);

                web_view_details.loadData(details, "text/html", "UTF-8");
            }

        }
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

        if(message == "You are not logged in!")
            requestIntervalHandler.showNotLoggedInView()
        else
            Toast.makeText(this, message as String, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun instantiateIntent(context: Context, plant: Plant): Intent {
            return Intent(context, PlantDetailsActivity::class.java).apply { this.putExtra(
                KEY_PLANT, plant
            ) }
        }
    }

    private fun setInitiateValues() {
        plant= intent.getSerializableExtra(KEY_PLANT) as Plant
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_OK)
        {
            requestIntervalHandler.hideNotLoggedInView()
            finishLoading()
        }

    }
}
