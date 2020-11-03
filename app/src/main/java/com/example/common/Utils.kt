package com.example.common

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager

object Utils {

    val FLICKR_PHOTO_URL = "https://farmKEY1.staticflickr.com/KEY2/KEY3_KEY4_m.jpg"

    fun hideKeyboard(activity: Activity) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val ib = activity.currentFocus?.windowToken
            imm.hideSoftInputFromWindow(ib, 0)
        } catch (e: Exception) {
        }
    }

    fun isInternetConnected(context: Context?): Boolean =
        context?.let {
            try {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return@let cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
            } catch (e: Exception) {
                return@let false
            }
        } ?: false
}