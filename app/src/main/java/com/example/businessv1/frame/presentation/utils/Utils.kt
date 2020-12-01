package com.example.businessv1.frame.presentation.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.i18n.phonenumbers.PhoneNumberUtil
object Utils {
    fun String.formatPhoneNumber(region: String): String? {
        val phoneNumberKit = PhoneNumberUtil.getInstance()
        val number = phoneNumberKit.parse(this, region)
        if (!phoneNumberKit.isValidNumber(number))
            return null

        return phoneNumberKit.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    }

    fun returnPhoneNumber(phone: String): String {
        if (phone != null) {
            val formattedPhone = phone.formatPhoneNumber("US")
            if (formattedPhone == null) {
                return Constant.NOT_FORMATED_NUMBER
            } else {
                return formattedPhone
            }
        }else{
            return Constant.NOT_FORMATED_NUMBER

        }
    }
    fun checkPermission(requireActivity: FragmentActivity):Boolean {
        if (ContextCompat.checkSelfPermission(requireActivity,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity,
                    Manifest.permission.CALL_PHONE)) {

            } else {
                ActivityCompat.requestPermissions(requireActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
            return false
        } else {
            // Permission has already been granted
            return true
        }
    }
}