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
        val date = extras.getSerializable("DATE")

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
                val i = Intent(this, OnboardingImage::class.java)
                i.putExtra("NAME", "$name")
                i.putExtra("GENDER", "$gender")
                i.putExtra("PREFERENCE", "$preference")
                i.putExtra("DATE", date)
                startActivity(i)

            }else{
                Toast.makeText(applicationContext, preference,Toast.LENGTH_SHORT).show()
            }
        }
    }
}