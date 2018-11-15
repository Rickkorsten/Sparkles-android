package com.fhict.sparklesandroid.onboarding

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.ColorInt
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.fhict.sparklesandroid.R

class OnboardingWelcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_welcome)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.sparkle_green))
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.sparkle_background))
        }

        // get button
        val getStarted_btn = findViewById<Button>(R.id.button)

        getStarted_btn.setOnClickListener{
            startActivity(Intent(this, OnboardingName::class.java))
            //.apply {
            //                putExtra("extra_1", value1)
            //                putExtra("extra_2", value2)
            //                putExtra("extra_3", value3)

        }

    }
}
