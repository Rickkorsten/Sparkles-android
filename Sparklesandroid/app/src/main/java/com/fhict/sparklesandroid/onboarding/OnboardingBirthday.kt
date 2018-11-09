package com.fhict.sparklesandroid.onboarding

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.fhict.sparklesandroid.R
import java.text.SimpleDateFormat
import java.util.*

class OnboardingBirthday : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_birthday)

        // get values
        val extras = intent.extras ?: return
        val name = extras.getString("NAME")
        // get elements
        val button : Button = findViewById(R.id.button)
        var calendarInput : EditText = findViewById(R.id.calendarEditText)
        var date: Date = SimpleDateFormat("dd/MM/yyyy").parse("30/11/1992")

        // calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        calendarInput.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,mYear, mMonth, mDay->
                // set to textView
                date = SimpleDateFormat("dd/MM/yyyy").parse("$mDay/$mMonth/$mYear")
                Toast.makeText(applicationContext, "$date",Toast.LENGTH_SHORT).show()
                calendarInput.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)
            // show dialog
            dpd.show()
        }

        button.setOnClickListener{

            val i = Intent(this, OnboardingGender::class.java)
            i.putExtra("NAME", "$name")
            i.putExtra("DATE", date)

            startActivity(i)

        }

    }
}
