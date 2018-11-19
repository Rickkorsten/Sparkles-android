package com.fhict.sparklesandroid.onboarding

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
            getWindow().setNavigationBarColor(getResources().getColor(R.color.sparkle_button_grey))
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.sparkle_background))
        }

        // get values
        val extras = intent.extras ?: return
        val name = extras.getString("NAME")
        // get elements
        val button : Button = findViewById(R.id.button)
        var calendarInput = findViewById<TextView>(R.id.calendarEditText)
        var date: Date = SimpleDateFormat("dd/MM/yyyy").parse("30/11/1992")

        // calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,mYear, mMonth, mDay->
            // set to textView
            date = SimpleDateFormat("dd/MM/yyyy").parse("$mDay/$mMonth/$mYear")
            calendarInput.setText("$mDay/$mMonth/$mYear")
        }, year, month, day)
        // show dialog
        dpd.show()


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.sparkle_green))
        }


        if( calendarInput.getText() !== null) {
            button.setBackgroundResource(R.drawable.onboard_button_green)
        }


        calendarInput.setOnClickListener{
            dpd.show()
        }

        button.setOnClickListener{
            val dataInput = calendarInput.text.toString()
            if (dataInput.isEmpty()){
                Toast.makeText(applicationContext, "Please give us your date of birth",Toast.LENGTH_SHORT).show()

            } else {
                val i = Intent(this, OnboardingGender::class.java)
                i.putExtra("NAME", "$name")
                i.putExtra("DATE", date)

                startActivity(i)
            }

        }

    }
}
