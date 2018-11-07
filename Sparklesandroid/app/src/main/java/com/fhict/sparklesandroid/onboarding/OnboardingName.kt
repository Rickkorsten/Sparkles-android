package com.fhict.sparklesandroid.onboarding

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        button.setOnClickListener{
            val inputtedText = name.text
            if(inputtedText.isNotEmpty()) {
                inputtedText.toString()
                startActivity(Intent(this, OnboardingGender::class.java)
                .apply {
                                putExtra("name", inputtedText)
            })
            }else{
                Toast.makeText(applicationContext,"please give us your name!",Toast.LENGTH_SHORT).show()
            }
        }

        name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // Do something after text changed
               // button.setBackgroundResource(R.drawable.onboard_button_green)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do something before text changed on EditText
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do something on text changed in EditText
                // Display the EditText change text on TextView real time
                if(p0?.length!! < 1){
                    Toast.makeText(applicationContext,"nothing",Toast.LENGTH_SHORT).show()
                    button.setBackgroundResource(R.drawable.onboard_button_grey)
                }else{
                    button.setBackgroundResource(R.drawable.onboard_button_green)
                }
            }
        })
    }
}
