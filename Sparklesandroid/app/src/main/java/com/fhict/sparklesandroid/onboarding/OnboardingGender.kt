package com.fhict.sparklesandroid.onboarding

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.fhict.sparklesandroid.R
import kotlinx.android.synthetic.main.activity_onboarding_gender.*

class OnboardingGender : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_gender)

        // get values
        val name = intent.getStringExtra("name")
        val maleBtn = findViewById<Button>(R.id.button_male)
        val femaleBtn = findViewById<Button>(R.id.button_female)
        val btn = findViewById<Button>(R.id.button)
        var gender : String = "nothing"

        maleBtn.setOnClickListener{
            maleBtn.setBackgroundResource(R.drawable.onboard_gender_button_active)
            femaleBtn.setBackgroundResource(R.drawable.onboard_gender_button_disabled)
            btn.setBackgroundResource(R.drawable.onboard_button_green)
            gender = "male"
        }

        femaleBtn.setOnClickListener{
            femaleBtn.setBackgroundResource(R.drawable.onboard_gender_button_active)
            maleBtn.setBackgroundResource(R.drawable.onboard_gender_button_disabled)
            btn.setBackgroundResource(R.drawable.onboard_button_green)
            gender = "female"
        }

        btn.setOnClickListener{
            if(gender !== "nothing") {
                startActivity(Intent(this, OnboardingImage::class.java)
                        .apply {
                            putExtra("name", name)
                            putExtra("gender", gender)
                        })
            }else{
                Toast.makeText(applicationContext, "$name  $gender",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
