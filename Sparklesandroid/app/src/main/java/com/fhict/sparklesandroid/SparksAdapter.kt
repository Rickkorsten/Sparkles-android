package com.fhict.sparklesandroid

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.fhict.sparklesandroid.data.model.PassedRelation
import com.fhict.sparklesandroid.data.model.Relation
import com.fhict.sparklesandroid.data.model.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.spark_row.view.*

class SparksAdapter(val passedRelationList: List<PassedRelation>): RecyclerView.Adapter<CustomViewHolder>() {

    val mockNames = listOf("Yolante", "Janinne", "Fenne", "Bertie", "Famke")
    var preferencesHelper: PreferencesHelper? = null
    private val gson = Gson()
    var user:User? = null

    override fun getItemCount(): Int {
        return passedRelationList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.spark_row, parent, false)
        preferencesHelper = PreferencesHelper(parent.context)
        user = gson.fromJson(preferencesHelper!!.user, User::class.java)

        return  CustomViewHolder(cellForRow)


    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val mockName = mockNames.get(position)

        val relation = passedRelationList.get(position)
        val userImage = holder.view.passed_relation_profile_image

        if (user?.firstName == relation.firstUserId.firstName){
            holder.view.textView_sparkName.text = relation.secondUserId.firstName
            Glide.with(holder?.view?.context).load(relation.secondUserId.userImage).into(userImage)
        } else {
            holder.view.textView_sparkName.text = relation.firstUserId.firstName
            Glide.with(holder?.view?.context).load(relation.firstUserId.userImage).into(userImage)
        }

        holder?.relationId = relation.id

    }

}

class CustomViewHolder(val view: View, var relationId: String? = null): RecyclerView.ViewHolder(view) {

    companion object {
        var RELATION_ID = "USER_ID"
    }

    init {
        view.setOnClickListener {
            val i = Intent(view.context, ChatActivity::class.java)
            i.putExtra(RELATION_ID, relationId)
            view.context.startActivity(i)
        }
    }
}