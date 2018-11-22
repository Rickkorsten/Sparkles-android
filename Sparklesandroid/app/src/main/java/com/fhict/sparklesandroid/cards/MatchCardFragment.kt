package com.fhict.sparklesandroid.cards

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.fhict.sparklesandroid.ChatActivity
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.onboarding.OnboardingPreference

class MatchCardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.match_card, container, false)

       val toMessageButton = view.findViewById<LinearLayout>(R.id.messageClick)

        toMessageButton.setOnClickListener {
            val i = Intent(context, ChatActivity::class.java)
            //i.putExtra("NAME", "$name")
            startActivity(i)
        }

        return view
    }
}