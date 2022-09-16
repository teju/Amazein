package com.amazein.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.amazein.R
import com.amazein.ui.activities.MainActivity



open class BaseDialogFragment : DialogFragment() {


    var v: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.let {
            it.window?.setBackgroundDrawableResource(R.color.transparent)
        }
    }


    fun home(): MainActivity {
        return activity as MainActivity
    }

}