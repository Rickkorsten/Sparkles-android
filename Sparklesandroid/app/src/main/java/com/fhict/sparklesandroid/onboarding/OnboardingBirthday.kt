package com.fhict.sparklesandroid.onboarding

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.fhict.sparklesandroid.R
import java.text.SimpleDateFormat
import java.util.*

class OnboardingBirthday : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_birthday)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.sparkle_button_grey)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = resources.getColor(R.color.sparkle_background)
        }

        // get values
        val extras = intent.extras ?: return
        val name = extras.getString("NAME")
        var dd = findViewById<EditText>(R.id.DD)
        var mm = findViewById<EditText>(R.id.MM)
        var yyyy = findViewById<EditText>(R.id.YYYY)
        val button : Button = findViewById(R.id.button)
        var date: Date = SimpleDateFormat("dd/MM/yyyy").parse("30/11/1992")

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        button.setOnClickListener{
           if (yyyy.length() === 4 && mm.length() === 2 && dd.length() === 2) {
               val ddText = dd.text
               val mmText = mm.text
               val yyyyText = yyyy.text
               date = SimpleDateFormat("dd/MM/yyyy").parse("$ddText/$mmText/$yyyyText")
               val i = Intent(this, OnboardingGender::class.java)
               i.putExtra("NAME", "$name")
               i.putExtra("DATE", date)

               startActivity(i)
           } else {
               Toast.makeText(applicationContext, "Please give us your date of birth",Toast.LENGTH_SHORT).show()
           }
        }

        dd.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (dd.length() === 2)
                {
                    mm.requestFocus()
                }
                dateCheck()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun afterTextChanged(s: Editable) { }

        })

        mm.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (mm.length() === 2)
                {
                    yyyy.requestFocus()
                }
                dateCheck()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun afterTextChanged(s: Editable) { }

        })

        yyyy.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                dateCheck()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun afterTextChanged(s: Editable) { }

        })

    }

    private fun dateCheck() {

        var dd = findViewById<EditText>(R.id.DD)
        var mm = findViewById<EditText>(R.id.MM)
        var yyyy = findViewById<EditText>(R.id.YYYY)
        val button : Button = findViewById(R.id.button)

        if (yyyy.length() === 4 && mm.length() === 2 && dd.length() === 2)
            {
                closeKeyboard()
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.navigationBarColor = resources.getColor(R.color.sparkle_green)
                }
                button.setBackgroundResource(R.drawable.onboard_button_green)
            }
            else {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.navigationBarColor = resources.getColor(R.color.sparkle_button_grey)
                }
                button.setBackgroundResource(R.drawable.onboard_button_grey)
            }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
