package com.fhict.sparklesandroid.cards

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.fhict.sparklesandroid.ChatActivity
import com.fhict.sparklesandroid.GlideOptions.bitmapTransform
import com.fhict.sparklesandroid.PreferencesHelper
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.data.model.Relation
import com.google.gson.Gson
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MatchCardFragment : Fragment() {

    private val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.match_card, container, false)

        val preferencesHelper = PreferencesHelper(view.context)
        val relation = gson.fromJson(preferencesHelper.relation, Relation::class.java)



        val imageView = view.findViewById<ImageView>(R.id.iv_profilepic)

        val multi = MultiTransformation<Bitmap>(
                BlurTransformation(80),
                RoundedCornersTransformation(60, 1))

        Glide.with(this).load(R.drawable.selfie)
                .apply(bitmapTransform(multi))
                .into(imageView)

        val toMessageButton = view.findViewById<LinearLayout>(R.id.messageClick)

        toMessageButton.setOnClickListener {
            val i = Intent(context, ChatActivity::class.java)
            i.putExtra("RELATION_ID", relation.id)
            startActivity(i)
        }



        return view
    }


}