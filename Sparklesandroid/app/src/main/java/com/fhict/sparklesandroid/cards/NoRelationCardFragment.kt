package com.fhict.sparklesandroid.cards

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.data.model.RelationResponse
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.APIService
import com.google.gson.Gson
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoRelationCardFragment : Fragment(){

    private var mAPIService: APIService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.no_relation_card, container, false)

        // btn
        val getRelationBtn = view.findViewById<Button>(R.id.button)

        // get helpers
        val gson = Gson()
        val preferencesHelper : PreferencesHelper = PreferencesHelper(view.context)
        val userpref = preferencesHelper.user

        getRelationBtn.setOnClickListener{
            if (!userpref.isEmpty()) {
                val user = gson.fromJson(userpref, User::class.java)
               searchAndSetRelation(user.id.toString(),user.preference.toString(),user.language.toString())

               // Toast.makeText(view!!.context,user.id.toString(), Toast.LENGTH_SHORT).show()


            }
        }

        return view
    }

    private fun searchAndSetRelation(id: String, preference: String, language: String ) {

       mAPIService?.searchAndSetRelation(id,preference,language)!!.enqueue(object : Callback<RelationResponse> {


           override fun onResponse(call: Call<RelationResponse>, response: Response<RelationResponse>) {
                if (response.isSuccessful()) {

                    val preferencesHelper = PreferencesHelper(view!!.context)

                    val gson = Gson()
                    val userObjectString = gson.toJson(response.body()!!)
                    preferencesHelper.user = userObjectString;

                    // get shared preference and create object
                    // val user = gson.fromJson(userObjectString, User::class.java)

                    Toast.makeText(view!!.context, response.body()!!.toString(), Toast.LENGTH_SHORT).show()

                }else {
                    Toast.makeText(view!!.context, "no response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RelationResponse>, t: Throwable) {
                Log.e("pipo de clown",t.message)
                Toast.makeText(view!!.context, "no response", Toast.LENGTH_SHORT).show()
            }
        })
    }
}