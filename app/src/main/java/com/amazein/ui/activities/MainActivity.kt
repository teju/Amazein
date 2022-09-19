package com.amazein.ui.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.amazein.R
import com.amazein.helper.BaseUIHelper
import com.amazein.helper.Helper
import com.amazein.ui.dialogs.NotifyDialogFragment
import com.amazein.ui.fragments.BaseFragment
import com.amazein.ui.fragments.MainTabFragment
import com.iapps.gon.etc.callback.NotifyListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {


    companion object {
        private var MAIN_FLOW_INDEX = 0
        private val MAIN_FLOW_TAG = "MainFlowFragment"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

    }

    fun initUI() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Handler().postDelayed(
            Runnable // Using handler with postDelayed called runnable run method

            {
                triggerMainProcess()
                imgSplash.visibility = View.GONE


            }, 1 * 2000
        )
    }

    private fun triggerMainProcess() {
        setFragment(MainTabFragment())
    }

    fun setFragment(fragment: Fragment) {
        try {
            val f = getSupportFragmentManager().beginTransaction()
            val list = getSupportFragmentManager().getFragments()
            for(frag in list){
                val current = fragment.javaClass.name
                val fragTag = frag.javaClass.name
                if(current.equals(fragTag)) {
                    return
                }
                if(frag.isVisible){
                    f.hide(frag)
                }
            }
            val backStateName: String = fragment.javaClass.name
            val fragmentPopped: Boolean =  getSupportFragmentManager().popBackStackImmediate(backStateName,
                0)

            MAIN_FLOW_INDEX = MAIN_FLOW_INDEX + 1
            f.add(R.id.layoutFragment, fragment, MAIN_FLOW_TAG + MAIN_FLOW_INDEX)
            f.setReorderingAllowed(true)
            if (!fragmentPopped) {
                f.addToBackStack(MAIN_FLOW_TAG).commitAllowingStateLoss()
            }
            BaseUIHelper().hideKeyboard(this)
        } catch (e: Exception) {
            Helper.logException(this, e)
        }
    }


    fun jumpToPreviousFlowThenGoTo(
        fullFragPackageNameThatStillExistInStack: String,
        targetFrag: Fragment
    ){
        jumpToPreviousFragment(fullFragPackageNameThatStillExistInStack)
        setFragment(targetFrag)
    }

    fun jumpToPreviousFragment(fullFragPackageNameThatStillExistInStack: String) {
        try {
            var indexTag: String? = null

            val f = getSupportFragmentManager().beginTransaction()
            var list = getSupportFragmentManager().getFragments()
            for(i in  0..(list.size - 1)){

                if(list.get(i).javaClass.name.equals(
                        fullFragPackageNameThatStillExistInStack,
                        ignoreCase = false
                    )){
                    indexTag = list.get(i).tag
                }

                if(list.get(i).isVisible){
                    f.hide(list.get(i))
                }
            }

            if(indexTag == null){
                onBackPressed()
            }else{

                val currentIndex = MAIN_FLOW_INDEX
                for(i in currentIndex downTo 0){
                    try {
                        if((MAIN_FLOW_TAG + i).equals(indexTag, ignoreCase = true)) break
                        getSupportFragmentManager().popBackStackImmediate()
                        MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
                    } catch (e: Exception) {
                        Helper.logException(this, e)
                    }
                }

                list = getSupportFragmentManager().getFragments()
                for(i in  0..(list.size - 1)){
                    if(list.get(i).tag.equals(indexTag, ignoreCase = false)){
                        f.show(list.get(i))
                        break
                    }
                }

                BaseUIHelper().hideKeyboard(this)
            }

        } catch (e: Exception) {
            Helper.logException(this, e)
        }
    }

    fun jumpToLastPreviousFragment(fullFragPackageNameThatStillExistInStack: String) {
        try {
            var indexTag: String? = null

            val f = getSupportFragmentManager().beginTransaction()
            var list = getSupportFragmentManager().getFragments()

            for(i in  0..(list.size - 1)){

                if(list.get(i).isVisible){
                    f.hide(list.get(i))
                }
            }

            for(i in  0..(list.size - 1)){

                if(list.get(i).javaClass.name.equals(
                        fullFragPackageNameThatStillExistInStack,
                        ignoreCase = false
                    )){
                    indexTag = list.get(i).tag
                    break;
                }
            }

            if(indexTag == null){
                onBackPressed()
            }else{

                val currentIndex = MAIN_FLOW_INDEX
                for(i in currentIndex downTo 0){
                    try {
                        if((MAIN_FLOW_TAG + i).equals(indexTag, ignoreCase = true)) break
                        getSupportFragmentManager().popBackStackImmediate()
                        MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
                    } catch (e: Exception) {
                        Helper.logException(this, e)
                    }
                }

                list = getSupportFragmentManager().getFragments()
                for(i in  0..(list.size - 1)){
                    if(list.get(i).tag.equals(indexTag, ignoreCase = false)){
                        f.show(list.get(i))
                        break
                    }
                }

                BaseUIHelper().hideKeyboard(this)
            }

        } catch (e: Exception) {
            Helper.logException(this, e)
        }
    }

    fun jumpBackPreviousFragment2(howManyTimes: Int){
        try {
            for(i in 0..(howManyTimes-1)) {
                getSupportFragmentManager().popBackStackImmediate()
                MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
            }
        } catch (e: Exception) {
            Helper.logException(this, e)
        }
    }

    fun setFragmentByReplace(frag: Fragment) {

        try {
            val f = getSupportFragmentManager().beginTransaction()
            MAIN_FLOW_INDEX = MAIN_FLOW_INDEX + 1
            f.replace(R.id.layoutFragment, frag, MAIN_FLOW_TAG + MAIN_FLOW_INDEX).addToBackStack(
                MAIN_FLOW_TAG
            ).commitAllowingStateLoss()
            BaseUIHelper().hideKeyboard(this)
        } catch (e: Exception) {
            Helper.logException(this, e)
        }

    }

    fun getCurrentFragmentByTag(): Fragment?{
        val fragmentManager = getSupportFragmentManager()
        val fragments = fragmentManager.getFragments()
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment!!.isVisible())
                    return fragment
            }
        }
        return null
    }

    fun clearFragment() {

        getSupportFragmentManager().popBackStack(
            MAIN_FLOW_TAG,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        for (i in MAIN_FLOW_INDEX downTo 0) {
            try {
                val fragment = getSupportFragmentManager().findFragmentByTag(MAIN_FLOW_TAG + i)
                if (fragment != null)
                    getSupportFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss()
            } catch (e: Exception) {
                Helper.logException(this, e)
            }

        }

        getSupportFragmentManager().popBackStack(
            "MAIN_TAB",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        MAIN_FLOW_INDEX = 0
    }
    fun exitApp() {
        finish()
    }
    override fun onBackPressed() {
        val f = getSupportFragmentManager().beginTransaction()
        val list = getSupportFragmentManager().getFragments()
        var foundVisible = false
        for(i in  0..(list.size - 1)){
            if(list.get(i).isVisible){
                if(list.get(i) is BaseFragment) {
                    foundVisible = true
                    (list.get(i) as BaseFragment).onBackTriggered()
                }
            }
        }

        if(!foundVisible)
            proceedDoOnBackPressed()
    }

    val currentFragment: Fragment
        get() = getSupportFragmentManager().findFragmentById(R.id.layoutFragment)!!


    fun proceedDoOnBackPressed(){
        BaseUIHelper().hideKeyboard(this)
        val f = getSupportFragmentManager().beginTransaction()
        val list = getSupportFragmentManager().getFragments()

        for(frag in list){
            if(frag.tag!!.contentEquals(MAIN_FLOW_TAG + (MAIN_FLOW_INDEX - 1))){
                f.show(frag)
            }
        }

        if (getSupportFragmentManager().getBackStackEntryCount() <= 1 || (currentFragment is MainTabFragment)) {
            Helper.showNotifyDialog(this, "",
                "Are you sure you want to exit the app?", "OK",
                "Cancel", object : NotifyListener {
                    override fun onButtonClicked(which: Int) {
                        if (which == NotifyDialogFragment.BUTTON_POSITIVE) {
                            exitApp()
                        }
                    }

                })

        } else {
            super.onBackPressed()
        }

        MAIN_FLOW_INDEX = MAIN_FLOW_INDEX - 1
    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }
    fun setOrShowExistingFragmentByTag(
        layoutId: Int,
        fragTag: String,
        backstackTag: String,
        newFrag: Fragment,
        listFragmentTagThatNeedToHide: ArrayList<String>
    ) {

        var foundExistingFragment = false

        val fragment = supportFragmentManager.findFragmentByTag(fragTag)
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            for (i in 0 until supportFragmentManager.fragments.size) {

                try {
                    val f = supportFragmentManager.fragments[i]

                    for (tag in listFragmentTagThatNeedToHide) {
                        try {
                            if (f.tag.toString().toLowerCase().equals(tag.toLowerCase())) {
                                transaction.hide(f)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            try {
                transaction.show(fragment).commit()
            } catch (e: Exception) {
                try {
                    transaction.show(fragment).commitAllowingStateLoss()
                } catch (e1: Exception) {
                    System.out.println("setOrShowExistingFragmentByTag Exception transaction " + e.toString())

                    e.printStackTrace()
                }

            }

            foundExistingFragment = true

        }

        if (!foundExistingFragment) {
            //setREmoveFragment(newFrag)
            setFragmentInFragment(layoutId, newFrag, fragTag, backstackTag)
        } else {
//            setREmoveFragment(newFrag)
//            setFragmentInFragment(layoutId, newFrag, fragTag, backstackTag)

        }

    }
    fun setFragmentInFragment(
        fragmentLayout: Int,
        frag: Fragment,
        tag: String,
        backstackTag: String
    ) {
        try {
            supportFragmentManager.beginTransaction().add(fragmentLayout, frag, tag).addToBackStack(
                backstackTag
            )
                .commit()
            Helper.hideKeyboard(this)
        } catch (e: Exception) {
            try {
                supportFragmentManager.beginTransaction().add(fragmentLayout, frag, tag).addToBackStack(
                    backstackTag
                )
                    .commitAllowingStateLoss()
                Helper.hideKeyboard(this)
            } catch (e1: Exception) {
                Helper.logException(this, e)
            }

        }

    }


}