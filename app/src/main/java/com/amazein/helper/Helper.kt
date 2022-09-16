package com.amazein.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.amazein.R
import com.amazein.library.helper.BaseHelper
import com.amazein.model.Error
import com.amazein.ui.dialogs.InvalidFragment
import com.amazein.ui.dialogs.NotifyDialogFragment
import com.iapps.gon.etc.callback.NotifyListener
import okhttp3.internal.and
import java.io.PrintWriter
import java.io.StringWriter

open class Helper {
    companion object {
        val listFragmentsMainTab: ArrayList<String>
            get() {
                val list = ArrayList<String>()

                list.add("FIRST_TAB")
                list.add("SECOND_TAB")
                list.add("THIRD_TAB")
                list.add("FOURTH_TAB")
                list.add("FIFTH_TAB")

                return list
            }

        fun logException(ctx: Context?, e: Exception?) {
            try {
                if (Constants.IS_DEBUGGING) {
                    if (Constants.IS_DEBUGGING) {
                        if (ctx != null)
                            Log.v(ctx.getString(R.string.app_name), getStackTrace(e!!))
                        else
                            print(getStackTrace(e!!))
                    }
                }
            } catch (e1: Exception) {
            }
        }

        fun hideKeyboard(activity: Activity) {
            val inputManager = activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (activity.currentFocus != null) {
                inputManager.hideSoftInputFromWindow(
                    activity.currentFocus!!
                        .windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                )
            } else {
                //			inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }

        fun getStackTrace(throwable: Throwable): String {
            val sw = StringWriter()
            val pw = PrintWriter(sw, true)
            throwable.printStackTrace(pw)
            return pw.toString()
        }

        open fun showNfcSettingsDialog(app: Activity) {
            AlertDialog.Builder(app)
                .setTitle("NFC is disabled")
                .setMessage("You must enable NFC to use this app.")
                .setPositiveButton(
                    android.R.string.yes
                ) { dialog, which -> app.startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
                .setNegativeButton(
                    android.R.string.no
                ) { dialog, which -> app.finish() }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        fun bytesToHex(bytes: ByteArray?): String? {
            val hexArray = "0123456789ABCDEF".toCharArray()
            if (bytes == null) return null
            val hexChars = CharArray(bytes.size * 2)
            for (j in bytes.indices) {
                val v: Int = bytes[j] and 0xFF
                hexChars[j * 2] = hexArray[v ushr 4]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            }
            return "0x" + String(hexChars)
        }

        open fun showNotifyDialog(
            activity: FragmentActivity,
            tittle: String?,
            messsage: String?,
            button_positive: String?,
            button_negative: String?,
            n: NotifyListener
        ) {
            val f = NotifyDialogFragment().apply {
                this.listener = n
            }
            f.notify_tittle = tittle
            f.notify_messsage = messsage
            f.button_positive = button_positive
            f.button_negative = button_negative
            f.isCancelable = false
            if (!BaseHelper().isEmpty(tittle) || !BaseHelper().isEmpty(messsage)) {
                if (!hasOpenedDialogs(activity!!)) {
                    f.show(activity!!.supportFragmentManager, NotifyDialogFragment.TAG)
                }
            }

        }

        open fun showErrorDialog(
            activity: FragmentActivity, error: Error, n: NotifyListener
        ) {
            val f = InvalidFragment()
            f.listener = n
            f.error = error
            f.isCancelable = false
            if (!hasOpenedDialogs(activity!!)) {
                f.show(activity!!.supportFragmentManager, InvalidFragment.TAG)
            }

        }

        open fun hasOpenedDialogs(activity: FragmentActivity): Boolean {
            val fragments: List<Fragment> = activity.supportFragmentManager.fragments
            if (fragments != null) {
                for (fragment in fragments) {
                    if (fragment is NotifyDialogFragment) {
                        return true
                    } else if (fragment is InvalidFragment) {
                        return true
                    }
                }
            }
            return false
        }

        fun isNetworkAvailable(ctx: Context): Boolean {
            try {
                val manager =
                    ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = manager.activeNetworkInfo

                var isAvailable = false
                if (networkInfo != null && networkInfo.isConnected) {
                    isAvailable = true
                }
                if (!isAvailable) {
                    logException(null, null)
                }
                return isAvailable
            } catch (e: Exception) {
                return true
            }

        }
    }

}