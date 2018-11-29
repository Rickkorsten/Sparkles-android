package com.fhict.sparklesandroid.tabs

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.SparksAdapter
import com.fhict.sparklesandroid.data.model.*
import com.fhict.sparklesandroid.data.remote.APIService
import com.fhict.sparklesandroid.data.remote.ApiUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.tab3_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Tab3Fragment: Fragment() {

    private var mAPIService: APIService? = null
    private val gson = Gson()
    var preferencesHelper: PreferencesHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View =  inflater.inflate(R.layout.tab3_fragment, container, false)

        val noSparks = view.findViewById<ConstraintLayout>(R.id.no_sparks)

        noSparks.visibility = View.GONE

        val sparksRecyclerView = view.findViewById<RecyclerView>(R.id.sparksRecyclerView)
        preferencesHelper = PreferencesHelper(view.context)
        val user = gson.fromJson(preferencesHelper?.user, User::class.java)

        // create api service
        mAPIService = ApiUtils.getAPIService()
        if (!preferencesHelper?.user!!.isEmpty()){
            getPassedRelations(user.id)

        }

        val layoutManager = LinearLayoutManager(activity)

        sparksRecyclerView.layoutManager = layoutManager

        sparksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    header.setBackgroundResource(R.drawable.header_background_no_border)
                } else
                {
                    header.setBackgroundResource(R.drawable.header_background)
                }
            }
        })

        return view
    }

    private fun getPassedRelations(relationId: String) {


        mAPIService?.getPassedRelations(relationId)!!.enqueue(object : Callback<PassedRelationsResponse> {
            override fun onResponse(call: Call<PassedRelationsResponse>, response: Response<PassedRelationsResponse>) {
                if (response.isSuccessful) {

                    val passedRelationString = response.body()!!.passedRelationsList

                    //preferencesHelper?.passedRelationList = gson.toJson(response.body()!!.passedRelationsList)

                    activity!!.runOnUiThread{
                        sparksRecyclerView.adapter = SparksAdapter(passedRelationString)
                    }

                    val noSparks = view!!.findViewById<ConstraintLayout>(R.id.no_sparks)

                    if (SparksAdapter(passedRelationString).itemCount == 0){
                        noSparks.visibility = View.VISIBLE
                    } else
                    {
                        noSparks.visibility = View.GONE
                    }

                } else {
                    val noSparks = view!!.findViewById<ConstraintLayout>(R.id.no_sparks)

                    noSparks.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<PassedRelationsResponse>, t: Throwable) {
                val noSparks = view!!.findViewById<ConstraintLayout>(R.id.no_sparks)

                noSparks.visibility = View.VISIBLE
            }
        })
    }

}
