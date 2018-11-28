package com.fhict.sparklesandroid.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.cards.MatchCardFragment
import com.fhict.sparklesandroid.cards.NoRelationCardFragment
import com.fhict.sparklesandroid.cards.SearchingCardFragment
import com.fhict.sparklesandroid.data.model.User
import com.google.gson.Gson

class Tab2Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.tab2_fragment, container, false)

        // get helpers
        val gson = Gson()
        val preferencesHelper = PreferencesHelper(view.context)
        val userpref = preferencesHelper.user

        // fragments
        val matchCardFragment=MatchCardFragment()
        val noRelationCardFragment=NoRelationCardFragment()
        val searchingCardFragment=SearchingCardFragment()

        // fragment manager
        val manager = fragmentManager
        val transaction = manager!!.beginTransaction()

        if (!userpref.isEmpty()) {
            val user = gson.fromJson(userpref, User::class.java)
            var relationStatus = user.status
            // Toast.makeText(view.context, relationStatus, Toast.LENGTH_SHORT).show()

            when (relationStatus) {
                "no_relation" -> transaction.replace(R.id.frameLayout, noRelationCardFragment).commit()
                "in_relation" ->  transaction.replace(R.id.frameLayout, matchCardFragment).commit()
                "searching" ->  transaction.replace(R.id.frameLayout, searchingCardFragment).commit()
            }

        }

        return view
    }



}