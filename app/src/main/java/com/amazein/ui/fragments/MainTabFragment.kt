package com.amazein.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amazein.R
import com.amazein.helper.Helper
import com.amazein.model.NavigationDataModel
import com.amazein.ui.adapters.DrawerItemCustomAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.nav_toolbar.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainTabFragment : BaseFragment() {
    var mDrawerToggle: ActionBarDrawerToggle? = null

    private var currentTab = FIRST_TAB
    var instance : Int = 0
    var selectedTab:Int = 0
    private var mNavigationDrawerItemTitles: ArrayList<String>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.main_fragment, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                setCurrentItem(item.itemId)
                return true
            }

        });
        initDrawer()
        setCurrentItem(currentTab)

    }

    fun initDrawer() {
        mNavigationDrawerItemTitles?.add("Connect")
        mNavigationDrawerItemTitles?.add("Fixtures")
        mNavigationDrawerItemTitles?.add("Table")
        setupToolbar();
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(false);
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeButtonEnabled(true);
        drawer_layout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle()
        mDrawerToggle?.syncState()

    }

    override fun onResume() {
        super.onResume()
    }

    fun setCurrentItem(which: Int) {

        if (which == FIRST_TAB) {
            currentTab = FIRST_TAB

            home().setOrShowExistingFragmentByTag(
                R.id.mainLayoutFragment, "FIRST_TAB",
                "MAIN_TAB", HomeFragment(), Helper.listFragmentsMainTab
            )

        } else if (which == SECOND_TAB) {
            currentTab = FIRST_TAB


        } else if (which == THIRD_TAB) {
            currentTab = THIRD_TAB

        } else if (which == FOURTH_TAB) {
            currentTab = FOURTH_TAB


        } else if (which == FIFTH_TAB) {
            currentTab = FIFTH_TAB


        }
    }

    fun setupToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

    }

    fun setupDrawerToggle() {
        mDrawerToggle = ActionBarDrawerToggle(
            activity,
            drawer_layout,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle?.syncState()
    }
    companion object {

        //region Constants Tab
        var FIRST_TAB = R.id.home //Homepage
        var SECOND_TAB = R.id.tests
        var THIRD_TAB = R.id.packages
        var FOURTH_TAB = R.id.services
        var FIFTH_TAB = R.id.profile


    }

}