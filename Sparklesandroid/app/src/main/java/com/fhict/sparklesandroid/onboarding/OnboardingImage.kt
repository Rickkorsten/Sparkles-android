package com.fhict.sparklesandroid.onboarding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.fhict.sparklesandroid.R
import android.content.Intent
import android.widget.Toast
import com.esafirm.imagepicker.features.ImagePicker
import com.fhict.sparklesandroid.GlideApp

class OnboardingImage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_image)

        val imageButton: ImageView = findViewById(R.id.imageView)

        imageButton.setOnClickListener{
            ImagePicker.create(this) // Activity or Fragment
                    .single()
                    .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // or get a single image only
            var userImage = ImagePicker.getFirstImageOrNull(data)
            Toast.makeText(applicationContext, userImage.path, Toast.LENGTH_SHORT).show()
            val imageButton: ImageView = findViewById(R.id.imageView)
            GlideApp.with(this).load(userImage.path).into(imageButton);


        }
    }


}
