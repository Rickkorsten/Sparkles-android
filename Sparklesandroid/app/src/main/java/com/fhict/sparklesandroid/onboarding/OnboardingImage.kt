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
import android.provider.Settings
import android.util.Log
import android.view.View
import com.fhict.sparklesandroid.data.remote.APIService
import android.widget.TextView
import com.fhict.sparklesandroid.data.model.User
import com.fhict.sparklesandroid.data.remote.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*


class OnboardingImage : AppCompatActivity() {

    private var mResponseTv: TextView? = null
    private var mAPIService: APIService? = null
    private var userImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_image)

        // get all values
        val extras = intent.extras ?: return
        val firstName = extras.getString("NAME")
        val gender = extras.getString("GENDER")
        val preference = extras.getString("PREFERENCE")
        val date : Date = extras.getSerializable("DATE") as Date

        // get resources
        val submitButton : Button = findViewById(R.id.button)
        val imageButton: ImageView = findViewById(R.id.imageView)

        // get android id
        val device_id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        // create api service
         mAPIService = ApiUtils.getAPIService();

        Toast.makeText(applicationContext, "$firstName $gender $preference $date",Toast.LENGTH_LONG).show()
        // click image
        imageButton.setOnClickListener {
            ImagePicker.create(this) // Activity or Fragment
                    .single()
                    .start()

        }
        // click bottom button
        submitButton.setOnClickListener {
            sendPost(firstName, gender, preference, date, device_id, userImage!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val button = findViewById<Button>(R.id.button)
            val imageButton: ImageView = findViewById(R.id.imageView)
            // or get a single image only
            userImage = ImagePicker.getFirstImageOrNull(data).path
            Toast.makeText(applicationContext, userImage, Toast.LENGTH_SHORT).show()
            GlideApp.with(this).load(userImage).into(imageButton);
            button.setBackgroundResource(R.drawable.onboard_button_green)

        }
    }

    fun sendPost(firstName: String, gender: String, preference: String, date: Date, device_id: String, image: String) {
        mAPIService?.saveUser(firstName, gender, date, device_id, image, preference)!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful()) {
                    showResponse(response.body().toString())
                    Log.i( "pipo de clown","post submitted to API." + response.body().toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown","Unable to submit post to API.")
            }
        })
    }

    fun showResponse(response: String) {
        if (mResponseTv?.getVisibility() === View.GONE) {
            mResponseTv?.setVisibility(View.VISIBLE)
        }
        mResponseTv?.setText(response)
    }


}
