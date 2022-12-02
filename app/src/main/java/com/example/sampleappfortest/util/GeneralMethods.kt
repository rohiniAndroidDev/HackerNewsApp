package com.example.sampleappfortest.util

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager

class GeneralMethods {
    companion object {
       /* fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }*/
lateinit var sharedPreferences:SharedPreferences


        fun setSharedPreference(
            context: Context,
            key: String?,
            value: Boolean
        ) {

            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun setSharedPreference(
            context: Context,
            key: String?,
            value: String
        ) {
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun removeSharedPreference(
            context: Context,
            key: String?
        ) {
            sharedPreferences.edit().remove(key).apply()
        }

        fun getValueByKey(
            context: Context,
            key: String?
        ): String? {
            return sharedPreferences.getString(key, "")
        }


    }
}