package com.openet.greenhup.features.home

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.openet.entities.Category
import com.openet.entities.MPackage
import com.openet.entities.Plant

import com.openet.greenhup.R
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.customviews.nonswipable_view_pager.SmartFragmentStatePagerAdapter
import com.openet.greenhup.features.categories_vendor.AdapterCategoriesList
import com.openet.greenhup.features.category_products.CategoryProductsActivity
import com.openet.greenhup.features.details.PlantDetailsActivity
import com.openet.greenhup.features.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.btn_search
import kotlinx.android.synthetic.main.fragment_home.et_search_query
import kotlinx.android.synthetic.main.fragment_home.lout_left_arrow_package
import kotlinx.android.synthetic.main.fragment_home.lout_loading_interval_view_container
import kotlinx.android.synthetic.main.fragment_home.lout_right_arrow_package
import kotlinx.android.synthetic.main.fragment_home.page_slider
import kotlinx.android.synthetic.main.fragment_home.rv_categories
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(),HomeView,AdapterHorizontalPlantsList.customButtonListener, AdapterCategoriesList.customButtonListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapter: SectionsPagerAdapter? = null
    var countDownTimer: CountDownTimer? =null
    private var packagesList: MutableList<MPackage> = ArrayList()
    val plantsHorizontalList: MutableList<Plant> = ArrayList()
    var adapterHorizontalPlantsList: AdapterHorizontalPlantsList?= null
    var adapterCategories: AdapterCategoriesList?= null
    val categoriesList: MutableList<Category> = ArrayList()

    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getHome()
        }
    }

    private val presenter: HomePresenter by lazy {
        HomeImplPresenter(this)
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        setupRequestHandlerView()
        presenter.getHome()


        btn_search.setOnClickListener {
            startActivity(SearchActivity.instantiateIntent(context!!, et_search_query.text.toString()))
        }


        adapterCategories= AdapterCategoriesList(requireContext(), categoriesList)
        adapterCategories?.setCustomButtonListner(this)
        rv_categories.adapter= adapterCategories


//        val plantsList: MutableList<Plant> = ArrayList()
//        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://h7h9j4n8.stackpathcdn.com/wp-content/uploads/2014/07/Classiс-plant-transparent.png"))
//        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://h7h9j4n8.stackpathcdn.com/wp-content/uploads/2014/07/Classiс-plant-transparent.png"))
//        plantsList.add(Plant("1","INDOOR", "Groom", "500", "https://h7h9j4n8.stackpathcdn.com/wp-content/uploads/2014/07/Classiс-plant-transparent.png"))
          adapterHorizontalPlantsList= AdapterHorizontalPlantsList(context!!,plantsHorizontalList)
          adapterHorizontalPlantsList?.setCustomButtonListner(this)
//        addItemsToGallery(plantsList)
          rv_plants_horizontal_list.adapter= adapterHorizontalPlantsList
//        addItemsToHorizontalList(plantsList)
//
//        val packagesList: MutableList<MPackage> = ArrayList()
//
//        packagesList.add(MPackage("1","Grromy","200",
//            "https://previews.123rf.com/images/jarretera/jarretera1908/jarretera190800180/129182700-amsterdam-the-netherlands-august-20-2019-green-package-or-garden-gourmet-incredible-burger-against-a.jpg",
//            plantsList
//            ))
//        packagesList.add(MPackage("1","Slim","300",
//            "https://packageinspiration.com/wp-content/uploads/2018/01/LeafcareMAIN-1.jpg",
//            plantsList
//        ))
//
//        packagesList.add(MPackage("1","Grromy","200",
//            "https://previews.123rf.com/images/jarretera/jarretera1908/jarretera190800180/129182700-amsterdam-the-netherlands-august-20-2019-green-package-or-garden-gourmet-incredible-burger-against-a.jpg",
//            plantsList
//        ))
//        packagesList.add(MPackage("1","Slim","300",
//            "https://packageinspiration.com/wp-content/uploads/2018/01/LeafcareMAIN-1.jpg",
//            plantsList
//        ))
//
//        addItemsToPackagesSlider(packagesList)



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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun addItemsToGallery(plantsList: MutableList<Plant>?) {
        if(plantsList!= null && plantsList.isNotEmpty()) {
            //page_slider.offscreenPageLimit = 3
            val sliderGalleryAdapter = AdapterHeaderGallery(plantsList, context!!)

            page_slider.adapter = sliderGalleryAdapter

            lout_left_arrow.setOnClickListener {
                if (page_slider.currentItem > 0)
                    page_slider.currentItem -= 1
            }
            lout_right_arrow.setOnClickListener {
                if (page_slider.currentItem < plantsList.size - 1)
                    page_slider.currentItem += 1
            }

            Log.e("HomeFragment", "list size= ${plantsList.size}")
            Log.e("HomeFragment", "slider size= ${page_slider.adapter?.count}")
        }
    }

    override fun addItemsToHorizontalList(plantsList: MutableList<Plant>?) {
        if(plantsList != null && plantsList.isNotEmpty())
            adapterHorizontalPlantsList?.addAll(plantsList)

        else
        {
            rv_plants_horizontal_list.visibility= View.GONE
            tv_featured_label.visibility= View.GONE
        }
    }

    override fun addItemsToPackagesSlider(packagesList: MutableList<MPackage>?) {

        if(packagesList != null && packagesList.isNotEmpty()) {
            if(packagesList.size>1) {
                lout_left_arrow_package.visibility = View.VISIBLE
                lout_right_arrow_package.visibility= View.VISIBLE
            }
            else
            {
                lout_left_arrow_package.visibility = View.GONE
                lout_right_arrow_package.visibility= View.GONE
            }

            this.packagesList = packagesList

            adapter = SectionsPagerAdapter(activity!!.supportFragmentManager, packagesList)
            view_pager_packages.adapter = adapter
            view_pager_packages.postInitViewPager()
            view_pager_packages.setScrollDurationFactor(6.0)

            updateSliderData()

            lout_left_arrow_package.setOnClickListener {
                if (view_pager_packages.currentItem > 0)
                    view_pager_packages.currentItem -= 1
                updateSliderData()
            }

            lout_right_arrow_package.setOnClickListener {
                if (view_pager_packages.currentItem <= packagesList.size) {
                    view_pager_packages.currentItem += 1
                    updateSliderData()
                }
            }

        }
        else
        {
            tv_packages_label.visibility= View.GONE
            view_pager_packages.visibility= View.GONE
            tv_package_name.visibility= View.GONE
            tv_package_price.visibility= View.GONE
            lout_left_arrow_package.visibility= View.GONE
            lout_right_arrow_package.visibility= View.GONE
        }
    }

    override fun addCategories(categories: MutableList<Category>) {
        Log.e("HomeFragment", "${categories.size}")
        adapterCategories?.addAll(categories)
    }

    override fun addMainOffer(plant: Plant?) {
        if(plant != null) {
            Glide.with(requireContext())
                .load(plant.imageUrl)
                .into(imageView2)
            plant.specsCats?.get(0)?.let {
                tv_offer_percentage.text = "---- ${getString(R.string.get)} ${it.discountPercentage} ${getString(R.string.off)} ---"
                tv_offer_name.text = plant.name
                tv_offer_details.text = plant.details
                btn_get_offer_now.setOnClickListener {
                    startActivity(PlantDetailsActivity.instantiateIntent(requireContext(), plant))
                }
                it.offerEndAt?.let { startCountdown((it.toLong() * 1000) - (Calendar.getInstance().timeInMillis)) }

            }
        }

        else
            lout_main_offer.visibility= View.GONE

    }

    private fun startCountdown(timeInMelliSeconds: Long) {
        countDownTimer = object : CountDownTimer(timeInMelliSeconds!!, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var n = millisUntilFinished / 1000

                val days = n / (24 * 3600)
                n = n % (24 * 3600)

                val hours = n / 3600
                n %= 3600;
                val minutes = n / 60

                n %= 60

                val seconds = n


                tv_day_offer.text = "%02d".format(days)
                tv_hours_offer.text = "%02d".format(hours)
                tv_offer_minutes.text = "%02d".format(minutes)
                tv_offer_seconds.text = "%02d".format(seconds)
            }

            override fun onFinish() {

            }
        }.start()
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

    fun updateSliderData()
    {
        tv_package_name.text= packagesList[view_pager_packages.currentItem].name
        tv_package_price.text= "${getString(R.string.currency)} ${packagesList[view_pager_packages.currentItem].price}"
    }

    override fun onItemHorizontalListClickListener(plant: Plant) {
        startActivity(PlantDetailsActivity.instantiateIntent(requireContext(), plant))
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, packagesList: MutableList<MPackage>) : SmartFragmentStatePagerAdapter(fm) {
        var mPackagesList: MutableList<MPackage> = ArrayList()
        init {
            mPackagesList= packagesList
        }

        override fun getItem(position: Int): Fragment {
            return PackageSlideFragment.newInstance(
                mPackagesList[position]
            )
        }

        override fun getCount(): Int {
            return mPackagesList.size
        }

    }

    override fun onStop() {
        countDownTimer?.cancel()
        super.onStop()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    override fun onItemMoreClickListner(productModel: Category) {
        startActivity(CategoryProductsActivity.instantiateIntent(requireContext(), productModel.id))
    }

    override fun omItemPlantClickListener(plant: Plant) {
        startActivity(PlantDetailsActivity.instantiateIntent(requireContext(), plant))
    }
}
