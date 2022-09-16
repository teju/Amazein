package com.amazein.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amazein.R
import com.amazein.library.helper.BaseHelper
import com.iapps.gon.etc.callback.NotifyListener
import kotlinx.android.synthetic.main.generic_dialog.*


class NotifyDialogFragment : BaseDialogFragment() {

    companion object {
        val TAG = "NotifyDialogFragment"
        val BUTTON_POSITIVE = 1
        val BUTTON_NEGATIVE = 0
    }
    var notify_tittle : String? = ""
    var notify_messsage : String? = ""
    var button_positive : String? = ""
    var button_negative : String? = ""
    var useHtml = false
    var listener: NotifyListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.generic_dialog, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(BaseHelper().isEmpty(notify_tittle)){
            vw_title.visibility = View.GONE
        }else{
            vw_title.visibility = View.VISIBLE
            vw_title.text = notify_tittle
        }

        if(BaseHelper().isEmpty(notify_messsage)){
            vw_text.visibility = View.GONE
        }else{
            vw_text.text = notify_messsage
        }

        btn_positive.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listener?.let {
                    listener?.onButtonClicked(BUTTON_POSITIVE)
                }
                dismiss()
            }
        })
        btn_negative.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listener?.let {
                    listener?.onButtonClicked(BUTTON_NEGATIVE)
                }
                dismiss()
            }
        })
        if(listener == null) {
            dismiss()
        }

    }

}