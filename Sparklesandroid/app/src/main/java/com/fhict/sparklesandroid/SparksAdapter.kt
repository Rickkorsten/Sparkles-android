package com.fhict.sparklesandroid

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhict.sparklesandroid.tabs.PassedRelationList
import kotlinx.android.synthetic.main.spark_row.view.*

class SparksAdapter(val passedRelationList: PassedRelationList): RecyclerView.Adapter<CustomViewHolder>() {

    val mockNames = listOf("Yolante", "Janinne", "Fenne", "Bertie", "Famke")

    override fun getItemCount(): Int {
        return mockNames.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.spark_row, parent, false)
        return  CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val mockName = mockNames.get(position)
        holder.view.textView_sparkName.text = mockName
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}