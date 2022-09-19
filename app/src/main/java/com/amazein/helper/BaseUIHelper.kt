package com.amazein.helper

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide


open class BaseUIHelper {

    open fun hideKeyboard(activity: Activity) {
        val inputManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!
                    .windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    open fun loadImage(context: Context?, url: String?, img: ImageView?) {
        Glide.with(context!!)
            .load(url).into(img!!)
    }


    companion object {
        fun setSnapper(recyclerView : RecyclerView) {
            val helper: SnapHelper = LinearSnapHelper()
            helper.attachToRecyclerView(recyclerView)
        }
    }


}