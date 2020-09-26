package com.am_development.greenhup.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.webkit.WebSettings
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.am_development.entities.KEY_PLANT
import com.am_development.entities.KEY_SEARCH_QUERY
import com.am_development.entities.Plant
import com.am_development.greenhup.R
import com.am_development.greenhup.customviews.CustomeFontTextView
import com.am_development.greenhup.features.home.HomeImplPresenter
import com.am_development.greenhup.features.home.HomePresenter
import com.am_development.greenhup.features.search.SearchActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import kotlin.math.roundToInt


class PlantDetailsActivity : AppCompatActivity(), PlantDetailsView {
    var plant: Plant?= null
    private val presenter: PlantDetailsPresenter by lazy {
        PlantDetailsImplPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setInitiateValues()

       /* plant?.let {
            presenter.getDetails(it.id)
        }*/

        plant?.let { addDetails(it) }


    }

    override fun addDetails(plant: Plant) {
        tv_category_name.text= plant.categoryName
        //rating_bar.rating= plant.
        Glide.with(this)
            .load(plant.imageUrl)
            .into(im_plant)
        tv_price.text= "Price: \$${plant.price}"
        tv_vendor_name.text= plant.vendorName

        addHtmlDescriptionToWebView(plant.details)

        if(plant.specsCats?.get(0)?.specifications?.size != 0) {
            val titlesRow= TableRow(this)
            var valueRow: TableRow?= TableRow(this)
            var titelsTextView: TextView?= null
            var attributeTextView: TextView?= null
            plant.specsCats?.get(0)?.specifications?.forEachIndexed { index, specification ->
                titelsTextView= TextView(this)
                TextViewCompat.setTextAppearance(titelsTextView!!, R.style.TitleTextAppearence)
                titelsTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
                if(index < plant.specsCats?.size!!-1) {
                    titelsTextView?.text= "${specification.specName} | "
                }
                else
                    titelsTextView?.text= specification.specName

                titlesRow.addView(titelsTextView)



            }
            table_specification.addView(titlesRow)

            plant.specsCats?.forEach{specsCatItem ->
                plant.specsCats?.get(0)?.specifications?.forEach {specefication->
                    //if(specsCatItem.specifications.filter { it.specId== specefication.specId} != null)

                        attributeTextView = TextView(this)

                    attributeTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
                    attributeTextView?.text= specsCatItem.specifications.filter { it.specId== specefication.specId}[0].value
                    valueRow?.addView(attributeTextView)
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


                table_specification.addView(valueRow)
                valueRow= TableRow(this)
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
        val webSettings: WebSettings = web_view_details.settings
        webSettings.javaScriptEnabled = true
        webSettings.lightTouchEnabled = true;
        webSettings.javaScriptEnabled = true;
        webSettings.setGeolocationEnabled(true);

        web_view_details.loadData(details, "text/html", "UTF-8");
    }

    override fun showLoading() {
    }

    override fun finishLoading() {
    }

    override fun connectionError(message: String?) {
    }

    override fun faildLoading(message: Any) {
    }

    companion object {
        fun instantiateIntent(context: Context, plant: Plant): Intent {
            return Intent(context, PlantDetailsActivity::class.java).apply { this.putExtra(
                KEY_PLANT, plant) }
        }
    }

    private fun setInitiateValues() {
        plant= intent.getSerializableExtra(KEY_PLANT) as Plant
    }
}
