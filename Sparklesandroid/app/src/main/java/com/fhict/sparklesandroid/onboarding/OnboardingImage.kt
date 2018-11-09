package com.fhict.sparklesandroid.onboarding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.fhict.sparklesandroid.R
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.esafirm.imagepicker.features.ImagePicker
import com.fhict.sparklesandroid.GlideApp

class OnboardingImage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_image)

        val extras = intent.extras ?: return
        val name = extras.getString("NAME")
        val gender = extras.getString("GENDER")
        val preference = extras.getString("PREFERENCE")
        val date = extras.getSerializable("DATE")


        Toast.makeText(applicationContext, "$name $gender $preference $date",Toast.LENGTH_LONG).show()

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
            var button = findViewById<Button>(R.id.button)
            Toast.makeText(applicationContext, userImage.path, Toast.LENGTH_SHORT).show()
            val imageButton: ImageView = findViewById(R.id.imageView)
            GlideApp.with(this).load(userImage.path).into(imageButton);
            button.setBackgroundResource(R.drawable.onboard_button_green)

        }
    }


}
