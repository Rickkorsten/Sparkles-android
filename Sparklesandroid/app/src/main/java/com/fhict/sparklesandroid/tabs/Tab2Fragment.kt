package com.fhict.sparklesandroid.tabs

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.fhict.sparklesandroid.R
import kotlinx.android.synthetic.*

class Tab2Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater!!.inflate(R.layout.tab2_fragment, container, false)

//        val toChatButton : Button = view.findViewById(R.id.button)
//
//        toChatButton.setOnClickListener {
//            val i = Intent(context, ChatActivity::class.java)
//            startActivity(i)
//        }

//        getSupportFragmentManager()
        return view
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

}