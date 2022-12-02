package com.example.sampleappfortest

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import com.example.sampleappfortest.common.ISLOGGEDIN
import com.example.sampleappfortest.home.presentation.ui.activities.HomeActivity
import com.example.sampleappfortest.login.presentation.activities.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPrefUtil: SharedPreferences
    var isLoggedin=false
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColorTransparent()
        setContentView(R.layout.activity_splash)

        sharedPrefUtil= PreferenceManager.getDefaultSharedPreferences(applicationContext)
        isLoggedin=sharedPrefUtil.getBoolean(ISLOGGEDIN,false)

        if(isLoggedin)
        {
            openMainActivity()
        }else
        {
            activityScope.launch {
                delay(2000)
                openLoginActivity()
            }
        }

    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    /*
    * set the status  bar as transparent
    * */
    private fun setStatusBarColorTransparent() {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }
}