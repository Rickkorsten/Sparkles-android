package com.fhict.sparklesandroid.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fhict.sparklesandroid.MainActivity
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.cards.MatchCardFragment
import com.fhict.sparklesandroid.cards.NoRelationCardFragment
import com.fhict.sparklesandroid.cards.SearchingCardFragment
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException

class Tab2Fragment : Fragment() {

    private var socket: Socket?=null
    val gson = Gson()
    // fragments
    private var mAPIService: APIService?=null
    val matchCardFragment=MatchCardFragment()
    val noRelationCardFragment=NoRelationCardFragment()
    val searchingCardFragment=SearchingCardFragment()
    var transaction: FragmentTransaction?=null
    var userpref: String?=null
    var user: User?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View=inflater.inflate(R.layout.tab2_fragment, container, false)

        // get helpers
        val gson=Gson()
        val preferencesHelper=PreferencesHelper(view.context)
        userpref=preferencesHelper.user

        // create api service
        mAPIService=ApiUtils.getAPIService()

        user=gson.fromJson(userpref, User::class.java)


        // fragment manager
        val manager=fragmentManager
        transaction=manager!!.beginTransaction()


        try {
            //if you are using a phone device you should connect to same local network as your laptop and disable your pubic firewall as well
            socket=IO.socket("https://sparklesapi.azurewebsites.net");
            //create connection
            socket!!.connect()
            // emit the event join along side with the nickname
            socket!!.emit("join", "rick");
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        socket!!.on("updateStatus") {
            //updateRelationStatus()
            updateRelationStatus()
        }

        updateCards()

        return view
    }

    fun updateRelationStatus() {

        mAPIService?.getUser(user?.id)!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val userObjectString= gson.toJson(response.body()!!)
                    userpref = userObjectString
                    //updateCards()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown", t.message)
            }
        })

    }

    private fun showToast() {
    }

    private fun updateCards() {

        if (!userpref!!.isEmpty()) {
            val relationStatus=user?.status

            when (relationStatus) {
                "no_relation" -> transaction?.replace(R.id.frameLayout, noRelationCardFragment)?.commit()
                "in_relation" -> transaction?.replace(R.id.frameLayout, matchCardFragment)?.commit()
                "searching" -> transaction?.replace(R.id.frameLayout, searchingCardFragment)?.commit()
            }

        }

    }


}