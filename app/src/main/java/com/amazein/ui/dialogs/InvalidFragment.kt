package com.amazein.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amazein.R
import com.amazein.model.Error
import com.iapps.gon.etc.callback.NotifyListener
import kotlinx.android.synthetic.main.fragment_invalid.*


class InvalidFragment : BaseDialogFragment() {

    var error: Error = Error()
    companion object {
        val TAG = "InvalidFragment"
    }
    var listener: NotifyListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_invalid, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        cancel.setOnClickListener {
            listener?.let {
                listener?.onButtonClicked(NotifyDialogFragment.BUTTON_POSITIVE)
            }


            dismiss()
        }
        if(!TextUtils.isEmpty(error.title)) {
            tv_title.text = error.title
        }
        if(!TextUtils.isEmpty(error.message)) {
            tv_desc.text = error.message
        }
    }
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.getWindow()?.setLayout(width, height)
        }
    }

}