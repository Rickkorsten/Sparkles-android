package com.fhict.sparklesandroid.tabs

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.SparksAdapter
import kotlinx.android.synthetic.main.tab3_fragment.*

class Tab3Fragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View =  inflater.inflate(R.layout.tab3_fragment, container, false)

        val noSparks = view.findViewById<ConstraintLayout>(R.id.no_sparks)

        noSparks.visibility = View.GONE

        val sparksRecyclerView = view.findViewById<RecyclerView>(R.id.sparksRecyclerView)


        val layoutManager = LinearLayoutManager(activity)

        sparksRecyclerView.layoutManager = layoutManager
        sparksRecyclerView.adapter = SparksAdapter()

        sparksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findFirstCompletelyVisibleItemPosition() === 0) {
                    header.setBackgroundResource(R.drawable.header_background_no_border)
                } else
                {
                    header.setBackgroundResource(R.drawable.header_background)
                }
            }
        })

        if (SparksAdapter().getItemCount() == 0){
            noSparks.visibility = View.VISIBLE
        } else
        {

        }


        return view
    }

}