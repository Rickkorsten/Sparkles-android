package com.fhict.sparklesandroid.onboarding

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.fhict.sparklesandroid.R

class OnboardingPreference : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_preference)

        val extras = intent.extras ?: return
        val name = extras.getString("NAME")
        val gender = extras.getString("GENDER")


        // get values
        var maleBtn : Button = findViewById(R.id.button_male)
        var femaleBtn : Button = findViewById(R.id.button_female)
        var btn : Button = findViewById(R.id.button)
        var preference : String = "nothing"

        maleBtn.setOnClickListener{
            maleBtn.setBackgroundResource(R.drawable.onboard_gender_button_active)
            femaleBtn.setBackgroundResource(R.drawable.onboard_gender_button_disabled)
            btn.setBackgroundResource(R.drawable.onboard_button_green)
            preference = "male"
        }

        femaleBtn.setOnClickListener{
            femaleBtn.setBackgroundResource(R.drawable.onboard_gender_button_active)
            maleBtn.setBackgroundResource(R.drawable.onboard_gender_button_disabled)
            btn.setBackgroundResource(R.drawable.onboard_button_green)
            preference = "female"
        }

        btn.setOnClickListener{
            if(preference !== "nothing") {
                startActivity(Intent(this, OnboardingImage::class.java)
                        .apply {
                            putExtra("NAME", "$name")
                            putExtra("GENDER", "$gender")
                            putExtra("PREFERENCE", "$preference")
                        })
            }else{
                Toast.makeText(applicationContext, preference,Toast.LENGTH_SHORT).show()
            }
        }
    }
}
