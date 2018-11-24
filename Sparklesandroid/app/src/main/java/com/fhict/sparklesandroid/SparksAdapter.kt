package com.fhict.sparklesandroid

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SparksAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.spark_row, parent, false)
        return  CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {

    }

}

class CustomViewHolder(v: View): RecyclerView.ViewHolder(v) {

}