package com.am_development.greenhup.features.home

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.am_development.entities.MPackage
import com.am_development.entities.Plant
import com.am_development.entities.SliderItem

import com.am_development.greenhup.R
import com.am_development.greenhup.customviews.nonswipable_view_pager.SmartFragmentStatePagerAdapter
import com.am_development.greenhup.features.details.DetailsActivity
import com.am_development.greenhup.features.package_details.PackageDetailsActivity
import com.am_development.greenhup.features.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
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
class HomeFragment : Fragment(),HomeView,AdapterHorizontalPlantsList.customButtonListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapter: SectionsPagerAdapter? = null
    var countDownTimer: CountDownTimer? =null
    private var packagesList: MutableList<MPackage> = ArrayList()
    val plantsHorizontalList: MutableList<Plant> = ArrayList()
    var adapterHorizontalPlantsList: AdapterHorizontalPlantsList?= null

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


        presenter.getHome()


        btn_search.setOnClickListener {
            startActivity(SearchActivity.instantiateIntent(context!!, et_search_query.text.toString()))
        }


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

    override fun addItemsToGallery(plantsList: MutableList<SliderItem>) {
        page_slider.offscreenPageLimit = 3
        val sliderGalleryAdapter = AdapterHeaderGallery(plantsList, context!!)

        page_slider.adapter = sliderGalleryAdapter
    }

    override fun addItemsToHorizontalList(plantsList: MutableList<Plant>) {
        adapterHorizontalPlantsList?.addAll(plantsList)
    }

    override fun addItemsToPackagesSlider(packagesList: MutableList<MPackage>) {

        this.packagesList= packagesList

        adapter = SectionsPagerAdapter(activity!!.supportFragmentManager, packagesList)
        view_pager_packages.adapter= adapter
        view_pager_packages.postInitViewPager()
        view_pager_packages.setScrollDurationFactor(6.0)

        lout_left_arrow_package.setOnClickListener {
            if(view_pager_packages.currentItem > 0)
            view_pager_packages.currentItem-=1
            updateSliderData()
        }

        lout_right_arrow_package.setOnClickListener {
            if(view_pager_packages.currentItem<=packagesList.size) {
                view_pager_packages.currentItem += 1
                updateSliderData()
            }
        }


    }

    override fun addMainOffer(plant: Plant) {
        tv_offer_percentage.text= "---- Get ${plant.discountPercentage} off ---"
        tv_offer_name.text= plant.name
        tv_offer_details.text= plant.details
        btn_get_offer_now.setOnClickListener {
            startActivity(Intent(activity, DetailsActivity::class.java))
        }
        plant.offerEndAt?.apply { startCountdown((this.toLong()*1000)-(Calendar.getInstance().timeInMillis)) }
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
    }

    override fun finishLoading() {
    }

    override fun connectionError(message: String?) {
    }

    override fun faildLoading(message: Any) {
    }

    fun updateSliderData()
    {
        tv_package_name.text= packagesList[view_pager_packages.currentItem].name
        tv_package_price.text= "KWT ${packagesList[view_pager_packages.currentItem].price}"
    }

    override fun onItemHorizontalListClickListener(plant: Plant) {
        startActivity(Intent(activity, DetailsActivity::class.java))
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

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}
