package com.fhict.sparklesandroid.cards

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.fhict.sparklesandroid.ChatActivity
import com.fhict.sparklesandroid.GlideOptions.bitmapTransform
import com.fhict.sparklesandroid.R
import com.fhict.sparklesandroid.onboarding.OnboardingPreference
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MatchCardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.match_card, container, false)

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
            //i.putExtra("NAME", "$name")
            startActivity(i)
        }



        return view
    }


}