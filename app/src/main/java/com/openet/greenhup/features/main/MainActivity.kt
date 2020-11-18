package com.openet.greenhup.features.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.openet.entities.ItemNovigtionMenu
import com.openet.greenhup.R
import com.openet.greenhup.base.BaseActivity
import com.openet.greenhup.features.about.AboutActivity
import com.openet.greenhup.features.categories_vendor.CategoriesActivity
import com.openet.greenhup.features.contact_us.ContactUsActivity
import com.openet.greenhup.features.services.ServicesActivity
import com.openet.greenhup.features.sign.SignActivity
import com.openet.greenhup.features.splash.SplashActivity
import com.google.android.material.tabs.TabLayout
import com.openet.greenhup.features.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_slide_menu.*

class MainActivity : BaseActivity(), AdapterSlideMenu.CustomeListener {

    var adapterSlideMenuList: AdapterSlideMenu? = null
    val menuItemsListNav: ArrayList<ItemNovigtionMenu> = ArrayList()
    private var drawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        setupActionBar()
        setupSlideMenu()
        checkLoginStatus()




    }

    private fun checkLoginStatus() {
        if(tokenUseCase.isLoggedIn)
        {
            tv_status_indicator.text= tokenUseCase.userName
            lout_btn_logout.visibility= View.VISIBLE
            lout_btn_logout.setOnClickListener {
                tokenUseCase.isLoggedIn= false
                startActivity(Intent(this, SplashActivity::class.java))
                finishAffinity()
            }
        } else {
            lout_btn_logout.visibility= View.GONE
            tv_status_indicator.text= getString(R.string.login)

            tv_status_indicator.setOnClickListener {
                if(!tokenUseCase.isLoggedIn) {
                    drawer_layout.closeDrawers()
                    startActivity(Intent(this, SignActivity::class.java))
                }
            }
        }
    }

    fun setupActionBar() {
        setSupportActionBar(toolbar_m)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.setDisplayShowTitleEnabled(false)
        actionBar!!.setDisplayUseLogoEnabled(true)
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle!!.onOptionsItemSelected(item)) {
            return true
        }
        else
            return super.onOptionsItemSelected(item)
    }

    private fun setupSlideMenu() {

        if (drawerToggle == null) {
            drawerToggle = object : ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar_m,
                R.string.drawer_open,
                R.string.drawer_close
            ) {



                override fun onDrawerClosed(view: View) {}

                override fun onDrawerOpened(drawerView: View) {

                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                override fun onDrawerStateChanged(newState: Int) {

                }

            }
            drawer_layout.setDrawerListener(drawerToggle)
        }

        drawerToggle!!.syncState()

        adapterSlideMenuList = AdapterSlideMenu(this, menuItemsListNav)


        adapterSlideMenuList?.setCustomButtonListner(this)
        lv_slideMenu.adapter = adapterSlideMenuList

        val menuItemsList = ArrayList<ItemNovigtionMenu>()

        menuItemsList.add(ItemNovigtionMenu(true,getString(R.string.home)))
        menuItemsList.add(ItemNovigtionMenu(true,getString(R.string.about)))
        menuItemsList.add(ItemNovigtionMenu(true,getString(R.string.shop)))
        menuItemsList.add(ItemNovigtionMenu(true,getString(R.string.services)))
        menuItemsList.add(ItemNovigtionMenu(true,getString(R.string.contact_us)))
        menuItemsList.add(ItemNovigtionMenu(true,getString(R.string.settings)))

        adapterSlideMenuList?.addAll(menuItemsList)
    }

    fun setupViewPager()
    {
        val tab= tabLayout.newTab()
        tab.setIcon(R.drawable.ic_home)
        tabLayout.addTab(tab)
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_shopping_cart))
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        viewPager!!.adapter = adapter
        viewPager.offscreenPageLimit=3
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onSelectedSlideMenuItem(position: Int) {
        drawer_layout.closeDrawers()
        when(position)
        {
            1 -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            2-> {
                startActivity(Intent(this, CategoriesActivity::class.java))
            }
            3-> {
                startActivity(Intent(this,ServicesActivity::class.java))
            }
            4-> {
                startActivity(Intent(this, ContactUsActivity::class.java))
            }
            5-> startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onResume() {
        checkLoginStatus()
        super.onResume()
    }
}
