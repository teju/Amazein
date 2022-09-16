package com.amazein.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.amazein.R
import com.amazein.helper.Constants
import com.amazein.helper.Helper
import com.amazein.ui.activities.MainActivity
import com.amazein.ui.dialogs.NotifyDialogFragment
import com.iapps.gon.etc.callback.NotifyListener
import com.iapps.gon.etc.callback.PermissionListener


open class BaseFragment : Fragment() {

    var v: View? = null
    var permissionsThatNeedTobeCheck: List<String>? = null
        private set
    var permissionListener: PermissionListener? = null
        private set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    open fun onBackTriggered(){
        home().proceedDoOnBackPressed()
    }
    var obsNoInternet: Observer<Boolean> = Observer { isHaveInternet ->
        try {
            if (!isHaveInternet) {
                if (activity == null ) return@Observer
                    Helper.showNotifyDialog(activity!!,
                        "", getString(R.string.no_connection),
                        getString(R.string.ok), "", object : NotifyListener {
                            override fun onButtonClicked(which: Int) {
                                if(which == NotifyDialogFragment.BUTTON_POSITIVE) {
                                    activity?.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                                }
                            }
                        })

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun home(): MainActivity {
        return activity as MainActivity
    }
    fun checkPermissions(permissionsThatNeedTobeCheck: List<String>, permissionListener: PermissionListener) {

        this.permissionsThatNeedTobeCheck = permissionsThatNeedTobeCheck
        this.permissionListener = permissionListener
        val permissionsNeeded = ArrayList<String>()
        val permissionsList = ArrayList<String>()

        for (s in permissionsThatNeedTobeCheck) {
            if (s.equals(Manifest.permission.CAMERA, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.CAMERA))
                    permissionsNeeded.add("Camera")
            } else if (s.equals(Manifest.permission.READ_CONTACTS, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
                    permissionsNeeded.add("Read Contacts")
            } else if (s.equals(Manifest.permission.WRITE_CONTACTS, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
                    permissionsNeeded.add("Write Contacts")
            } else if (s.equals(Manifest.permission.READ_EXTERNAL_STORAGE, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
                    permissionsNeeded.add("Read External Storage")
            } else if (s.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    permissionsNeeded.add("Write External Storage")
            } else if (s.equals(Manifest.permission.RECEIVE_SMS, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.RECEIVE_SMS))
                    permissionsNeeded.add("Read SMS")
            } else if (s.equals(Manifest.permission.ACCESS_FINE_LOCATION, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                    permissionsNeeded.add("ACCESS FINE LOCATION")
            } else if (s.equals(Manifest.permission.ACCESS_COARSE_LOCATION, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                    permissionsNeeded.add("ACCESS COARSE LOCATION")
            } else if (s.equals(Manifest.permission.READ_SMS, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.READ_SMS))
                    permissionsNeeded.add("Read SMS")
            } else if (s.equals(Manifest.permission.CALL_PHONE, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
                    permissionsNeeded.add("Call Phone")
            } else if (s.equals(Manifest.permission.RECORD_AUDIO, ignoreCase = true)) {
                if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
                    permissionsNeeded.add("Record Audio")
            }
        }

        if (permissionsList.size > 0) {
            if (permissionsNeeded.size > 0) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    permissionsList.toTypedArray(),
                    Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )
                return
            }
            ActivityCompat.requestPermissions(
                activity!!, permissionsList.toTypedArray(),
                Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
            )
            return
        } else {
            permissionListener.onPermissionAlreadyGranted()
        }
    }
    private fun checkPermission(permission: String): Boolean {
        return if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    private fun addPermission(permissionsList: MutableList<String>, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission))
                return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        try {
            if (requestCode == Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {

                var isAllGranted = false
                val index = 0
                for (permission in permissionsThatNeedTobeCheck!!) {
                    if (permission.equals(Manifest.permission.CAMERA, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.READ_CONTACTS, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.WRITE_CONTACTS, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.RECEIVE_SMS, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.READ_SMS, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    } else if (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION, ignoreCase = true)) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break
                        } else {
                            isAllGranted = true
                        }
                    }
                }
                if (isAllGranted) {
                    permissionListener!!.onCheckPermission(permissions[index], true)
                } else {
                    permissionListener!!.onCheckPermission(permissions[index], false)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}