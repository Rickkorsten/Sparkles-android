package com.fhict.sparklesandroid.onboarding

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.fhict.sparklesandroid.R

class OnboardingName : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_name)
        // get elements
        var name = findViewById<EditText>(R.id.nameInput)
        var button = findViewById<Button>(R.id.button)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.sparkle_button_grey)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = resources.getColor(R.color.sparkle_background)
        }

        button.setOnClickListener{
            val inputtedText = name.text
            if(inputtedText.isNotEmpty()) {


                val i = Intent(this, OnboardingBirthday::class.java)
                i.putExtra("NAME", "$inputtedText")
                startActivity(i)


            } else {
                Toast.makeText(applicationContext,"please give us your name!",Toast.LENGTH_SHORT).show()
            }
        }

        name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length!! < 1){
                    Toast.makeText(applicationContext,"nothing",Toast.LENGTH_SHORT).show()
                    button.setBackgroundResource(R.drawable.onboard_button_grey)
                }else{
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.navigationBarColor = resources.getColor(R.color.sparkle_green)
                    }
                    button.setBackgroundResource(R.drawable.onboard_button_green)
                }
            }
        })
    }
}
