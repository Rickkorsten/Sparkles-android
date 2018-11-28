package com.fhict.sparklesandroid.cards

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.fhict.sparklesandroid.ChatActivity
import com.fhict.sparklesandroid.GlideOptions.bitmapTransform
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.SparksAdapter
import com.fhict.sparklesandroid.data.model.*
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.google.gson.Gson
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.spark_row.view.*
import kotlinx.android.synthetic.main.tab3_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchCardFragment : Fragment() {

    private val gson = Gson()
    private var mAPIService: APIService? = null
    var preferencesHelper: PreferencesHelper? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.match_card, container, false)

        preferencesHelper = PreferencesHelper(view.context)
        val user = gson.fromJson(preferencesHelper!!.user, User::class.java)

        // getActiveRelations(user.id.toString())

        // create api service
        mAPIService = ApiUtils.getAPIService()

<<<<<<< HEAD
=======
>>>>>>> 1045190f08a67f055a738023ebb367dd0ba9bc8a
        return view
    }

    private fun getSparkInfo(userId: String) {
        mAPIService?.getUser(userId)!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    preferencesHelper?.mainSparkName= response.body()!!.firstName
                    preferencesHelper?.mainSparkImage= response.body()!!.userImage

                    setMainSpark()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown", t.message)
            }
        })
    }

    private fun setMainSpark() {

        val imageView = view!!.findViewById<ImageView>(R.id.iv_profilepic)

        val multi = MultiTransformation<Bitmap>(
                BlurTransformation(80),
                RoundedCornersTransformation(60, 1))

        Glide.with(this).load(preferencesHelper?.mainSparkImage)
                .apply(bitmapTransform(multi))
                .into(imageView)


    }

    private fun searchAndSetRelation(id: String, preference: String, language: String ) {

        mAPIService?.searchAndSetRelation(id,preference,language)!!.enqueue(object : Callback<RelationResponse> {


            override fun onResponse(call: Call<RelationResponse>, response: Response<RelationResponse>) {
                if (response.isSuccessful()) {

                    val preferencesHelper = PreferencesHelper(view!!.context)
                    if (response.body()!!.confirmation == "match found and created new relation"){
                        val gson = Gson()
                        val relationObjectString = gson.toJson(response.body()!!.getdata())
                        preferencesHelper.relation = relationObjectString

                        val user = gson.fromJson(preferencesHelper!!.user, User::class.java)
                        val relation = gson.fromJson(preferencesHelper?.relation, Relation::class.java)

                        if (user?.id == relation.firstUserId){
                            getSparkInfo(relation.secondUserId)
                        } else {
                            getSparkInfo(relation.firstUserId)
                        }

                       // val toMessageButton = view.findViewById<LinearLayout>(R.id.messageClick)

//                        toMessageButton.setOnClickListener {
//                            val i = Intent(context, ChatActivity::class.java)
//                            i.putExtra("RELATION_ID", relation.id)
//                            startActivity(i)
//                        }

                    }
                    Toast.makeText(view!!.context, response.body()!!.getdata().toString(), Toast.LENGTH_SHORT).show()
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

    private fun getActiveRelations(relationId: String) {


        mAPIService?.getActiveRelations(relationId)!!.enqueue(object : Callback<RelationSingle> {
            override fun onResponse(call: Call<RelationSingle>, response: Response<RelationSingle>) {
                if (response.isSuccessful) {

                    Toast.makeText(view!!.context, response.body().toString(), Toast.LENGTH_SHORT).show()


                }
            }

            override fun onFailure(call: Call<RelationSingle>, t: Throwable) {
                val noSparks = view!!.findViewById<ConstraintLayout>(R.id.no_sparks)

                noSparks.visibility = View.VISIBLE
            }
        })
    }
}