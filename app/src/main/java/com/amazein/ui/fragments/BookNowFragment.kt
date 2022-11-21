package com.amazein.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.amazein.R
import com.amazein.library.helper.AutoCompletePopupWindow
import com.amazein.library.helper.Helper
import com.amazein.library.helper.adapter.GenericDDAdapterNoFilter
import com.amazein.ui.adapters.*
import kotlinx.android.synthetic.main.fragment_book_now.*
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BookNowFragment : BaseFragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_book_now, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setGender()
        val contactString = "I have read and agree to"


        cb_tc.setText(Html.fromHtml(contactString))
        cb_tc.setMovementMethod(LinkMovementMethod.getInstance())
        val tc = SpannableString(" Terms and Conditions")
        val pp = SpannableString(" Privacy Policy")
        tc.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, 0, tc.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        tc.setSpan(
            ForegroundColorSpan(activity?.resources?.getColor(R.color.colorAccent)!!),
            0,
            tc.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        cb_tc.append(tc)
        cb_tc.append(" and")
        pp.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, 0, pp.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        pp.setSpan(
            ForegroundColorSpan(activity?.resources?.getColor(R.color.colorAccent)!!),
            0,
            pp.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        cb_tc.append(pp)
    }

    override fun onResume() {
        super.onResume()
    }

    fun initView() {
        tv_address.setOnClickListener(this)


    }

    fun setGender () {
        var genderList = ArrayList<String>()
        genderList.add("Male")
        genderList.add("Female")
        var genderAdapter =
            GenericDDAdapterNoFilter(
                activity,
                genderList
            )
        genderAdapter.setFilterable(true)
        val acPopupGender = AutoCompletePopupWindow(
            activity,
            getString(R.string.please_select_gender),
            actGender.text.toString(),
            AutoCompletePopupWindow.GENERIC_DD_NOFILTER_ADAPTER,
            actGender,
            genderAdapter,
            genderAdapter,
            OnItemClickListener { parent, view, position, id ->
                actGender.setText(genderAdapter.getItem(position))

            })
        actGender.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Helper.hideSoftKeyboard(activity)
                acPopupGender.show()
            }
            true
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_address -> {
                val address = AddressFragment()
                home().setFragment(address)
            }

        }
    }
}