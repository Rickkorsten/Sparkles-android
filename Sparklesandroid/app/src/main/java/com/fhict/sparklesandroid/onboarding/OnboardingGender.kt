package com.fhict.sparklesandroid.onboarding

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.fhict.sparklesandroid.R

class OnboardingGender : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_gender)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.sparkle_button_grey))
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.sparkle_background))
        }

        // get values
        val extras = intent.extras ?: return
        val name = extras.getString("NAME")
        val date = extras.getSerializable("DATE")


        val maleBtn = findViewById<Button>(R.id.button_male)
        val femaleBtn = findViewById<Button>(R.id.button_female)
        val btn = findViewById<Button>(R.id.button)
        var gender : String = "nothing"

        maleBtn.setOnClickListener{
            maleBtn.setBackgroundResource(R.drawable.onboard_gender_button_active)
            femaleBtn.setBackgroundResource(R.drawable.onboard_gender_button_disabled)
            btn.setBackgroundResource(R.drawable.onboard_button_green)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.sparkle_green))
            }
            gender = "male"
        }

        femaleBtn.setOnClickListener{
            femaleBtn.setBackgroundResource(R.drawable.onboard_gender_button_active)
            maleBtn.setBackgroundResource(R.drawable.onboard_gender_button_disabled)
            btn.setBackgroundResource(R.drawable.onboard_button_green)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.sparkle_green))
            }
            gender = "female"
        }

        btn.setOnClickListener{
            if(gender !== "nothing") {
                val i = Intent(this, OnboardingPreference::class.java)
                i.putExtra("NAME", "$name")
                i.putExtra("GENDER", "$gender")
                i.putExtra("DATE", date)
                startActivity(i)
            }else{
                Toast.makeText(applicationContext, "$name  $gender",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
