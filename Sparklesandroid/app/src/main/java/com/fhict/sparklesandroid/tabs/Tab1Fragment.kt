package com.fhict.sparklesandroid.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.data.model.User
import com.google.gson.Gson
import java.util.*

class Tab1Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View =  inflater!!.inflate(R.layout.tab1_fragment, container, false)

        val gson = Gson()

        val preferencesHelper = PreferencesHelper(view.context)
        val prefUser = preferencesHelper.user

        if (!prefUser.isEmpty()){
            val user = gson.fromJson(prefUser, User::class.java)
            var nameTextView = view.findViewById<TextView>(R.id.user_name)

            var name = user.firstName
            var nameAndDate = name + " (" + ")"
            //nameTextView.text = user.toString()
        }

        return view

    }

    private fun getAge(year: Int, month: Int, day: Int): String {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.set(year, month, day)

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val ageInt = age

        return ageInt.toString()
    }
}