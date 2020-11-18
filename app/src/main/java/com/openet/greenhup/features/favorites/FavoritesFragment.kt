package com.openet.greenhup.features.favorites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.openet.entities.Plant

import com.openet.greenhup.R
import com.openet.greenhup.core.RequestIntervalHandler2
import com.openet.greenhup.features.categories_vendor.AdapterPlants
import com.openet.greenhup.features.details.PlantDetailsActivity
import com.openet.greenhup.features.sign.SignActivity
import kotlinx.android.synthetic.main.fragment_favorites.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment(), AdapterPlants.customButtonListener, FavoritesView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val PAGE_START = 1
    private var isLoadingV: Boolean = false
    private var isLastPageV: Boolean = false
    private var TOTAL_PAGES: Int = 50
    private lateinit var requestIntervalHandler: RequestIntervalHandler2
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    val totalPageCount = 20
    private var requestIsFinished= true
    private var currentPage = PAGE_START

    private val presenter: FavoritesPresenter by lazy {
        FavoritesImplPresenter(this, requireContext())
    }
    private val tryAgainTriggerObserever = Observer<Int> {
        when (it) {
            1 -> presenter.getFavorites(currentPage)
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

    private val REQUEST_CODE_LOGIN = 1

    var adapterPlantsList: AdapterPlants?= null
    val plantsList: MutableList<Plant> = ArrayList()

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
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRequestHandlerView()
        setupProductsListRecyclerView()
        //implementScrolListener()
        currentPage = PAGE_START
        presenter.getFavorites(currentPage)




     /*   val planList: MutableList<Plant> = ArrayList()

        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))
        planList.add(Plant("1","INDOOR", "Groom", "500", "https://cdn.shopify.com/s/files/1/0150/6262/products/the-sill_snake-plant-laurentii_variant_medium_hyde_blush_1200x.jpg?v=1593114598"))

        addPlants(planList)*/

    }

    private fun setupProductsListRecyclerView() {
        adapterPlantsList = AdapterPlants(context!!, plantsList)
        adapterPlantsList?.setCustomButtonListner(this)
        rv_favorites.adapter= adapterPlantsList
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
         * @return A new instance of fragment FavoritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemPlantsClickListener(plant: Plant) {
        startActivity(PlantDetailsActivity.instantiateIntent(requireContext(), plant))
    }

    override fun addPlants(plantsList: MutableList<Plant>) {
        adapterPlantsList?.addAll(plantsList)
    }

    override fun setLastPageTrue() {
        isLastPageV = true
    }

    override fun addLoadingFooter() {
        adapterPlantsList?.addLoadingFooter()
    }

    override fun removeLoadingFooter() {
        adapterPlantsList?.removeLoadingFooter()
    }

    override fun showRetryAdapter() {
        adapterPlantsList?.showRetry(true,"Error")
    }

    override fun setIsLoadingFalse() {
        isLoadingV = false
    }

    override fun showEmptyViewForList() {
        finishLoading()
        //view_empty_list.visibility = View.VISIBLE
    }

    override fun setRequestIsFinished(requestIsFinished: Boolean) {
        this.requestIsFinished= requestIsFinished
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_OK)
        {
            presenter.getFavorites(currentPage)
        }

    }
}
