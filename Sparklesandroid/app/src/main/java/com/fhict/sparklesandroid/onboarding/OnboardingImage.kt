package com.fhict.sparklesandroid.onboarding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.fhict.sparklesandroid.R
import android.content.Intent
import android.os.Build
import android.widget.Button
import android.widget.Toast
import com.esafirm.imagepicker.features.ImagePicker
import com.fhict.sparklesandroid.GlideApp
import android.provider.Settings
import android.util.Log
import android.view.View
import com.fhict.sparklesandroid.data.remote.APIService
import android.widget.TextView
import com.fhict.sparklesandroid.MainActivity
import com.fhict.sparklesandroid.PreferencesHelper
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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.sparkle_button_grey)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = resources.getColor(R.color.sparkle_background)
        }

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
        // val device_id : String = "nieuwId123"

        // create api service
         mAPIService = ApiUtils.getAPIService()

        Toast.makeText(applicationContext, "$firstName $gender $preference $date",Toast.LENGTH_LONG).show()
        // click image
        imageButton.setOnClickListener {
            ImagePicker.create(this) // Activity or Fragment
                    .single()
                    .start()

        }
        // click bottom button
        submitButton.setOnClickListener {
            Toast.makeText(applicationContext, "$firstName, $gender, $preference, $device_id, $date, $userImage!!",Toast.LENGTH_LONG).show()

            sendPost(firstName, gender, preference, device_id, date)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val button = findViewById<Button>(R.id.button)
            val imageButton: ImageView = findViewById(R.id.imageView)
            // or get a single image only
            userImage = ImagePicker.getFirstImageOrNull(data).path
            Toast.makeText(applicationContext, userImage, Toast.LENGTH_SHORT).show()
            GlideApp.with(this).load(userImage).into(imageButton)
            button.setBackgroundResource(R.drawable.onboard_button_green)
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = resources.getColor(R.color.sparkle_green)
            }
        }
    }

    fun sendPost(firstName: String, gender: String, preference: String, device_id: String, date: Date) {
        mAPIService?.saveUser(firstName, gender, device_id, preference, date)!!.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {
                    showResponse(response.body().toString())
                    Log.i( "pipo de clown","post submitted to API." + response.body().toString())
                    Toast.makeText(applicationContext, "created account", Toast.LENGTH_SHORT).show()

                    val preferencesHelper = PreferencesHelper(applicationContext)
<<<<<<< HEAD
                    preferencesHelper.didOnboarding = true
                    preferencesHelper.deviceId = device_id
                    preferencesHelper.firstName = firstName
=======
                    preferencesHelper.didOnboarding = true;
                    preferencesHelper.deviceId = device_id;
                    // preferencesHelper.firstName = firstName;
>>>>>>> develop

                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("pipo de clown",t.message)
            }
        })
    }

    fun showResponse(response: String) {
        if (mResponseTv?.visibility === View.GONE) {
            mResponseTv?.visibility = View.VISIBLE
        }
        mResponseTv?.text = response
    }


}
