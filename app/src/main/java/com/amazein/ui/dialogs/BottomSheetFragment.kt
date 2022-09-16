package com.amazein.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.amazein.R
import com.amazein.model.home.Home
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.home_bottom_sheet_dialog, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))

        val tv_desc = contentView.findViewById<TextView>(R.id.tv_desc)
        val tv_title = contentView?.findViewById<TextView>(R.id.tv_title)
        val btn_cancel = contentView?.findViewById<Button>(R.id.btn_cancel)
        btn_cancel?.setOnClickListener{
            dismiss()
        }

        tv_desc?.text = popUpHomeData?.data?.popup?.description
        tv_title?.text = popUpHomeData?.data?.popup?.title
    }

    companion object {
        private var popUpHomeData: Home? = null
        fun newInstance(homeData: Home?): BottomSheetFragment {
            popUpHomeData = homeData
            return BottomSheetFragment()
        }
    }
}