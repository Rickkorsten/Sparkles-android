package com.fhict.sparklesandroid

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fhict.sparklesandroid.R

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setSparkTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun setSparkTheme() {

        val preferencesHelper = PreferencesHelper(applicationContext)

        if (preferencesHelper.darkMode) {
            setTheme(R.style.DarkTheme)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = resources.getColor(R.color.black)
                window.statusBarColor = resources.getColor(R.color.black)
            }
        } else {
            setTheme(R.style.AppTheme_NoTitleBar)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = resources.getColor(R.color.sparkle_background)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = resources.getColor(R.color.sparkle_background)
            }
        }
    }
}
